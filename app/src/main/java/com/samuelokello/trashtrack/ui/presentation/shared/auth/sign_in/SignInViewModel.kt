package com.samuelokello.trashtrack.ui.presentation.shared.auth.sign_in

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.samuelokello.trashtrack.data.local.User
import com.samuelokello.trashtrack.util.Utils.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignInViewModel : ViewModel() {
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

            SignInEvent.NavigateToProfileCreation -> {
                _state.update {
                    it.copy(navigateToUserHome = true)
                }
            }

            else -> {}
        }
    }

    private fun firebaseSignIn(email: String, password: String, context: Context) {
        showLoading()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                hideLoading()
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
                    Toast.makeText(context, "Authentication Successful", Toast.LENGTH_LONG).show()
                    auth.currentUser
                    if (isAdmin(User(email = email))) {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                navigateToAdminHome = true
                            )
                        }
                    } else {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                navigateToUserHome = true)
                        }
                    }
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    when (val exception = task.exception) {
                        is FirebaseAuthInvalidCredentialsException ->
                            Toast.makeText(context, "Invalid credentials.", Toast.LENGTH_LONG)
                                .show()

                        is FirebaseAuthUserCollisionException ->
                            Toast.makeText(context, "User collision.", Toast.LENGTH_LONG).show()

                        else ->
                            Toast.makeText(context, "Authentication failed.", Toast.LENGTH_LONG)
                                .show()
                    }
                    _state.update {
                        it.copy(navigateToUserHome = false)
                    }
                }
            }

    }

    private fun showLoading() {
        _state.update { it.copy(isLoading = true) }
    }

    private fun hideLoading() {
        _state.update { it.copy(isLoading = false) }
    }
}

private fun isAdmin(user: User): Boolean {
    // Replace "adminUsername" and "adminPassword" with your actual default admin credentials
    val adminUsername = "admin@trashtrack.com"

    return user.email == adminUsername
}

data class SignInUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val navigateToSignUp: Boolean = false,
    val navigateToUserHome: Boolean = false,
    val navigateToAdminHome: Boolean = false
)

sealed interface SignInEvent {
    data class EmailChanged(val email: String) : SignInEvent
    data class PasswordChanged(val password: String) : SignInEvent
    data class SignInClicked(
        val email: String,
        val password: String,
        val context: Context,
        val user: User
    ) : SignInEvent

    object NavigateToSignUp : SignInEvent
    object NavigateToProfileCreation : SignInEvent
}