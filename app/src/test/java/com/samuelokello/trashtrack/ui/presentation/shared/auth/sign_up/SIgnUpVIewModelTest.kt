package com.samuelokello.trashtrack.ui.presentation.shared.auth.sign_up

import com.google.common.base.Verify.verify
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.samuelokello.trashtrack.ui.presentation.shared.auth.sign_up.SIgnUpVIewModel
import com.samuelokello.trashtrack.ui.presentation.shared.auth.sign_up.SignUpEvent
import com.samuelokello.trashtrack.util.Utils.auth
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class SIgnUpVIewModelTest {
    private val viewModel = SIgnUpVIewModel()

    @Test
    fun `when EmailChanged event is received email is updated`() {
        val email = "test@example.com"

        viewModel.onEvent(SignUpEvent.EmailChanged(email))

        assert((viewModel.state.value.email) == email)
    }

    @Test
    fun `when password event is received, password is updated in state`() {
        val password = "Test@p#!"

        viewModel.onEvent(SignUpEvent.PasswordChanged(password))

        assert((viewModel.state.value.password) == password)
    }

    @Test
    fun `when NavigateToSignIn event is received, navigateToSignIn is set to true in state`() {
        viewModel.onEvent(SignUpEvent.NavigateToSignIn(true))

        assert((viewModel.state.value.navigateToSignIn))
    }

    @Test
    fun `when NavigateToSignIn event is received with false, navigateToSignIn remains false in state`() {
        viewModel.onEvent(SignUpEvent.NavigateToSignIn(false))

        assert((viewModel.state.value.navigateToSignIn))
    }

//    @Test
//    fun `signUp with valid email and password results in successful authentication`() {
//        val email = "test@example.com"
//        val password = "password123"
//        val context = mock(Context::class.java)
//
//        viewModel.signUp(email, password, context)
//
//        verify(auth).createUserWithEmailAndPassword(email, password)
//        assert(!viewModel.state.value.isLoading)
//        assert(viewModel.state.value.navigateToSignIn)
//    }
//
//    @Test
//    fun `signUp with weak password results in FirebaseAuthWeakPasswordException`() {
//        val email = "test@example.com"
//        val password = "weak"
//        val context = mock(Context::class.java)
//
//        `when`(auth.createUserWithEmailAndPassword(email, password)).thenThrow(
//            FirebaseAuthWeakPasswordException::class.java
//        )
//
//        viewModel.signUp(email, password, context)
//
//        verify(auth).createUserWithEmailAndPassword(email, password)
//        assertThat(viewModel.state.value.isLoading).isFalse()
//        assertThat(viewModel.state.value.navigateToSignIn).isFalse()
//    }
//
//    @Test
//    fun `signUp with malformed email results in FirebaseAuthInvalidCredentialsException`() {
//        val email = "invalid_email"
//        val password = "password123"
//        val context = mock(Context::class.java)
//
//        `when`(auth.createUserWithEmailAndPassword(email, password)).thenThrow(
//            FirebaseAuthInvalidCredentialsException::class.java
//        )
//
//        viewModel.signUp(email, password, context)
//
//        verify(auth).createUserWithEmailAndPassword(email, password)
//        assertThat(viewModel.state.value.isLoading).isFalse()
//        assertThat(viewModel.state.value.navigateToSignIn).isFalse()
//    }
//
//    @Test
//    fun `signUp with existing email results in FirebaseAuthUserCollisionException`() {
//        val email = "existing@example.com"
//        val password = "password123"
//        val context = mock(Context::class.java)
//
//        `when`(auth.createUserWithEmailAndPassword(email, password)).thenThrow(
//            FirebaseAuthUserCollisionException::class.java
//        )
//
//        viewModel.signUp(email, password, context)
//
//        verify(auth).createUserWithEmailAndPassword(email, password)
//        assertThat(viewModel.state.value.isLoading).isFalse()
//        assertThat(viewModel.state.value.navigateToSignIn).isFalse()
//    }
}