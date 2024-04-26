package com.samuelokello.trashtrack.ui.presentation.shared.auth.sign_up

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser
import com.samuelokello.trashtrack.data.local.AppDatabase
import com.samuelokello.trashtrack.data.local.User
import com.samuelokello.trashtrack.data.repository.RoomUserRepository
import com.samuelokello.trashtrack.ui.presentation.shared.auth.sign_in.SignInEvent
import com.samuelokello.trashtrack.util.Utils.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SIgnUpVIewModel (private val conext: Context): ViewModel() {

    private val _state = MutableStateFlow(SignUpUiState())
    val state = _state.asStateFlow()

    private val userRepositroty = RoomUserRepository(userDao = AppDatabase.getDatabase(conext).userDao())

    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.EmailChanged -> {
                _state.update {
                    it.copy(email = event.email)
                }
            }

            is SignUpEvent.PasswordChanged -> {
                _state.update {
                    it.copy(password = event.password)
                }
            }

            is SignUpEvent.SignUpClicked -> {
                signUp(event.email, event.password, event.context)
            }

            is SignUpEvent.NavigateToSignIn -> {
                _state.update {
                    it.copy(navigateToSignIn = true)
                }
            }
        }
    }

    private fun signUp(email: String, password: String, context: Context) {
        showLoading()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                hideLoading()
                if (task.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    user.let { user ->
                        insertUser(user!!)
                    }
                    Toast.makeText(context, "Authentication Successful", Toast.LENGTH_SHORT).show()
                    _state.update {
                        it.copy(
                            isLoading = false,
                            navigateToSignIn = true
                        )
                    }
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    _state.update {
                        it.copy(
                            isLoading = false,
                            navigateToSignIn = false
                        )
                    }
                    when (val exception = task.exception) {
                        is FirebaseAuthWeakPasswordException ->
                            Toast.makeText(context, "The password is too weak.", Toast.LENGTH_SHORT)
                                .show()

                        is FirebaseAuthInvalidCredentialsException ->
                            Toast.makeText(
                                context,
                                "The email address is malformed.",
                                Toast.LENGTH_SHORT
                            ).show()

                        is FirebaseAuthUserCollisionException ->
                            Toast.makeText(
                                context,
                                "The email address is already in use.",
                                Toast.LENGTH_SHORT
                            ).show()

                        else ->
                            Toast.makeText(context, "Authentication Failed", Toast.LENGTH_SHORT)
                                .show()
                    }
                }
            }
    }

    private fun insertUser(user: FirebaseUser) {
        val localUser = User(
            email = user.email!!,
        )
        viewModelScope.launch {
            userRepositroty.insertUser(localUser)
        }
    }

    private fun showLoading() {
        _state.update { it.copy(isLoading = true) }
    }

    private fun hideLoading() {
        _state.update { it.copy(isLoading = false) }
    }
}

data class SignUpUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val navigateToSignIn: Boolean = false
)

sealed interface SignUpEvent {
    data class EmailChanged(val email: String) : SignUpEvent
    data class PasswordChanged(val password: String) : SignUpEvent
    data class SignUpClicked(val email: String, val password: String, val context: Context) :
        SignUpEvent

    data class NavigateToSignIn(val navigateToSignIn: Boolean) : SignUpEvent
}