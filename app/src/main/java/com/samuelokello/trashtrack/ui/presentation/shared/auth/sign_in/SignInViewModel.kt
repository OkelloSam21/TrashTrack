package com.samuelokello.trashtrack.ui.presentation.shared.auth.sign_in

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.samuelokello.trashtrack.util.Utils.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignInViewModel :ViewModel() {
    private val _state = MutableStateFlow(SignInUiState())
    val state = _state.asStateFlow()

    fun onEvent(event: SignInEvent) {
        when (event) {
            is SignInEvent.EmailChanged -> {
                _state.update {
                    it.copy(email = event.email)
                }
            }
            is SignInEvent.PasswordChanged -> {
                _state.update {
                    it.copy(password = event.password)
                }
            }
            is SignInEvent.SignInClicked -> {
                firebaseSignIn(_state.value.email, _state.value.password, event.context)
            }
            is SignInEvent.NavigateToSignUp -> {
                _state.update {
                    it.copy(navigateToSignUp = true)
                }
            }

            SignInEvent.NavigateToHome -> {
                _state.update {
                    it.copy(navigateToHome = true)
                }
            }
        }
    }

    private fun firebaseSignIn(email: String, password: String, context: Context) {
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "signInWithEmail:success")
                Toast.makeText(context, "Authentication Successful", Toast.LENGTH_LONG).show()
                val user = auth.currentUser
                _state.update {
                    it.copy(navigateToHome = true)
                }
            } else {
                Log.w(TAG, "signInWithEmail:failure", task.exception)
                when (val exception = task.exception) {
                    is FirebaseAuthInvalidCredentialsException ->
                        Toast.makeText(context, "Invalid credentials.", Toast.LENGTH_LONG).show()
                    is FirebaseAuthUserCollisionException ->
                        Toast.makeText(context, "User collision.", Toast.LENGTH_LONG).show()
                    else ->
                        Toast.makeText(context, "Authentication failed.", Toast.LENGTH_LONG).show()
                }
                _state.update {
                    it.copy(navigateToHome = false)
                }
            }
        }
}
}
data class SignInUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val navigateToSignUp: Boolean = false,
    val navigateToHome: Boolean = false
)
sealed interface SignInEvent {
    data class EmailChanged(val email: String) : SignInEvent
    data class PasswordChanged(val password: String) : SignInEvent
    data class SignInClicked (val email: String, val password: String, val context: Context): SignInEvent
    object NavigateToSignUp : SignInEvent
    object NavigateToHome: SignInEvent
}