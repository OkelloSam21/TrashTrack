package com.samuelokello.trashtrack.ui.presentation.shared.auth.create_profile

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.samuelokello.trashtrack.util.Utils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CreateProfileViewModel : ViewModel() {

    private val _state = MutableStateFlow(CreateProfileUiState())
    val state = _state.asStateFlow()

fun onEvent(event: CreateProfileEvent) {
    when (event) {
        is CreateProfileEvent.NameChanged -> _state.update { it.copy(name = event.name) }
        is CreateProfileEvent.PhoneChanged -> _state.update { it.copy(phone = event.phone) }
        is CreateProfileEvent.LocationChanged -> _state.update { it.copy(location = event.location) }
        is CreateProfileEvent.CreateProfileClicked -> createProfile(event.context)
    }
}

    private fun createProfile(context: Context) {
        val userId = Utils.auth.currentUser?.uid ?: return
        val user = User(state.value.name, state.value.phone, state.value.location)

        _state.update { it.copy(isLoading = true) }

        Utils.database.getReference("users").child(userId).setValue(user)
            .addOnCompleteListener {
                Toast.makeText(context, "Profile created successfully", Toast.LENGTH_SHORT).show()
                _state.update { it.copy(isLoading = false, navigateToHome = true) }
            }
            .addOnFailureListener {exception ->
                Toast.makeText(context, "Profile creation failed", Toast.LENGTH_SHORT).show()
                _state.update { it.copy(isLoading = false, navigateToHome = false, error = exception.message.toString() ?: "Unknown error") }
            }
    }
}

data class CreateProfileUiState(
    val name: String = "",
    val phone: String = "",
    val location: String = "",
    val isLoading: Boolean = false,
    val error: String = "",
    val navigateToHome: Boolean = false
)

sealed interface CreateProfileEvent {
    data class NameChanged(val name: String) : CreateProfileEvent
    data class PhoneChanged(val phone: String) : CreateProfileEvent
    data class LocationChanged(val location: String) : CreateProfileEvent
    data class CreateProfileClicked(val context: Context) : CreateProfileEvent
}

data class User(
    val name: String = "",
    val phone: String = "",
    val location: String = ""
)