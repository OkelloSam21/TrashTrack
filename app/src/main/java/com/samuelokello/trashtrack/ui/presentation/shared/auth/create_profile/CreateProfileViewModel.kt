package com.samuelokello.trashtrack.ui.presentation.shared.auth.create_profile

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.firebase.database.FirebaseDatabase
import com.samuelokello.trashtrack.util.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class CreateProfileViewModel @Inject constructor(
    private val placesClient: PlacesClient,
    private val dataBase: FirebaseDatabase
) : ViewModel() {

    private val _state = MutableStateFlow(CreateProfileUiState())
    val state = _state.asStateFlow()

    private val searchJob = Job()

    fun onEvent(event: CreateProfileEvent) {
        when (event) {
            is CreateProfileEvent.NameChanged -> _state.update { it.copy(name = event.name) }
            is CreateProfileEvent.PhoneChanged -> _state.update { it.copy(phone = event.phone) }
            is CreateProfileEvent.LocationChanged -> {
                _state.update { it.copy(location = event.location) }
                fetchAutocompletePredictions(event.location)
            }

            is CreateProfileEvent.CreateProfileClicked -> createProfile(event.context)
            is CreateProfileEvent.SuggestionSelected -> _state.update { it.copy(location = event.suggestion) }
        }
    }

    private fun fetchAutocompletePredictions(query: String) {
        if (query.isEmpty()) {
            _state.update { it.copy(autoCompleteSuggestions = emptyList()) }
        }

        searchJob.cancelChildren() // cancel any previous searches
        viewModelScope.launch(Dispatchers.Main + searchJob) {
            delay(300)
            val request = placesClient
                .findAutocompletePredictions(
                    FindAutocompletePredictionsRequest.builder()
                        .setQuery(query)
                        .build()
                )
            try {
                val response = request.await()
                _state.update { it.copy(autoCompleteSuggestions = response.autocompletePredictions) }
            } catch (e: Exception) {
                _state.update { it.copy(autoCompleteSuggestions = emptyList()) }
            }
        }
    }

    private fun createProfile(context: Context) {
        val userId = Utils.auth.currentUser?.uid ?: return
        val user = User(state.value.name, state.value.phone, state.value.location)

        _state.update { it.copy(isLoading = true) }

        dataBase.getReference("users").child(userId).setValue(user)
            .addOnCompleteListener {
                Toast.makeText(context, "Profile created successfully", Toast.LENGTH_SHORT).show()
                _state.update { it.copy(isLoading = false, navigateToSignIn = true) }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(context, "Profile creation failed", Toast.LENGTH_SHORT).show()
                _state.update {
                    it.copy(
                        isLoading = false,
                        navigateToSignIn = false,
                        error = exception.message.toString()
                    )
                }
            }
    }
}

data class CreateProfileUiState(
    val name: String = "",
    val phone: String = "",
    val location: String = "",
    val autoCompleteSuggestions: List<AutocompletePrediction> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = "",
    val navigateToSignIn: Boolean = false
)

sealed interface CreateProfileEvent {
    data class NameChanged(val name: String) : CreateProfileEvent
    data class PhoneChanged(val phone: String) : CreateProfileEvent
    data class LocationChanged(val location: String) : CreateProfileEvent
    data class SuggestionSelected(val suggestion: String) : CreateProfileEvent
    data class CreateProfileClicked(val context: Context) : CreateProfileEvent
}

data class User(
    val name: String = "",
    val phone: String = "",
    val location: String = ""
)