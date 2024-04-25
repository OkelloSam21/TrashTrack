package com.samuelokello.trashtrack.ui.presentation.worker.request_pick_up

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.type.Date
import com.samuelokello.trashtrack.util.Utils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RequestViewModel : ViewModel() {
    private val _state = MutableStateFlow(RequestUiState())
    val state = _state.asStateFlow()

    fun onEvent(event: RequestEvent) {

        when (event) {
            is RequestEvent.WasteTypeChanged -> _state.update { it.copy(wasteType = event.wasteType) }
            is RequestEvent.OnSubmitClicked -> reportWaste(event.context)
            is RequestEvent.WasteDateChanged -> _state.update { it.copy(date = event.date ?: Date.getDefaultInstance()) }
            is RequestEvent.WasteLocationChanged -> _state.update { it.copy(wasteLocation = event.wasteLocation) }
        }
    }

    private fun reportWaste(context: Context) {
        // Report waste to the server
        val userId = Utils.auth.currentUser?.uid ?: return
        val waste = Waste(
            state.value.wasteType,
            state.value.date.toString(),
            state.value.wasteLocation,
        )

        _state.update { it.copy(isLoading = true) }

        Utils.database.getReference("requested_waste").child(userId).setValue(waste)
            .addOnCompleteListener {
                Toast.makeText(context, "Waste request  sent successfully", Toast.LENGTH_LONG).show()
                _state.update { it.copy(isLoading = false, navigateToHome = true) }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(context, "Failed to send request", Toast.LENGTH_LONG).show()
                _state.update { it.copy(isLoading = false, navigateToHome = false, error = exception.message.toString() ?: "Unknown error") }
            }
    }
}

data class Waste(
    val wasteType: String = "",
    val date: String = "",
    val wasteLocation: String = "",
)

data class RequestUiState(
    val isLoading: Boolean = false,
    val wasteType: String = "",
    val wasteLocation: String = "",
    val date: Any = Date.getDefaultInstance(),
    val navigateToHome: Boolean = false,
    val error: String = "",
)

sealed interface RequestEvent {
    data class WasteTypeChanged(val wasteType: String) : RequestEvent
    data class WasteDateChanged(val date: java.util.Date?) : RequestEvent
    data class WasteLocationChanged(val wasteLocation: String) : RequestEvent
    data class OnSubmitClicked(
        val wasteType: String,
        val wasteLocation: String,
        val date: Any,
        val context: Context
    ) : RequestEvent
}