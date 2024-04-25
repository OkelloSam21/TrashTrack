package com.samuelokello.trashtrack.ui.presentation.worker.report_waste

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.type.Date
import com.samuelokello.trashtrack.util.Utils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ReportViewModel : ViewModel() {
    private val _state = MutableStateFlow(ReportUiState())
    val state = _state.asStateFlow()

    fun onEvent(event: ReportEvent) {

        when (event) {
            is ReportEvent.WasteTypeChanged -> _state.update { it.copy(wasteType = event.wasteType) }
            is ReportEvent.OnSubmitClicked -> reportWaste(event.context)
            is ReportEvent.WasteDateChanged -> _state.update { it.copy(date = event.date ?: Date.getDefaultInstance()) }
            is ReportEvent.WasteLocationChanged -> _state.update { it.copy(wasteLocation = event.wasteLocation) }
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

        Utils.database.getReference("reported_waste").child(userId).setValue(waste)
            .addOnCompleteListener {
                Toast.makeText(context, "Waste reported successfully", Toast.LENGTH_SHORT).show()
                _state.update { it.copy(isLoading = false, navigateToHome = true) }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(context, "Failed to report waste", Toast.LENGTH_SHORT).show()
                _state.update { it.copy(isLoading = false, navigateToHome = false, error = exception.message.toString() ?: "Unknown error") }
            }
    }
}

data class Waste(
    val wasteType: String = "",
    val date: String = "",
    val wasteLocation: String = "",
)

data class ReportUiState(
    val isLoading: Boolean = false,
    val wasteType: String = "",
    val wasteLocation: String = "",
    val date: Any = Date.getDefaultInstance(),
    val navigateToHome: Boolean = false,
    val error: String = "",
)

sealed interface ReportEvent {
    data class WasteTypeChanged(val wasteType: String) : ReportEvent
    data class WasteDateChanged(val date: java.util.Date?) : ReportEvent
    data class WasteLocationChanged(val wasteLocation: String) : ReportEvent
    data class OnSubmitClicked(
        val wasteType: String,
        val wasteLocation: String,
        val date: Any,
        val context: Context
    ) : ReportEvent
}