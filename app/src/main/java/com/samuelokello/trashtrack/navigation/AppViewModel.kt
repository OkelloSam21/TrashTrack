package com.samuelokello.trashtrack.navigation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.samuelokello.trashtrack.data.repository.UserRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AppViewModel(private val userRepository: UserRepository) : ViewModel() {
    val navigationEvent = MutableLiveData<Screen>()

    fun checkUserStatus() {
        viewModelScope.launch {
            try {
                val userExists = userRepository.userExists()
                val userProfileCreated = userRepository.userProfileCreated()

                if (userExists && userProfileCreated) {
                    navigationEvent.value = Screen.HOME
                } else if (userExists) {
                    navigationEvent.value = Screen.SIGN_IN
                } else {
                    navigationEvent.value = Screen.WELCOME
                }
            } catch (e: Exception) {
                // Handle error cases
            }
        }
    }
}

 // UserRepository
// class FirebaseUserRepository : UserRepository {
//     private val auth = FirebaseAuth.getInstance()
//     private val firestore = FirebaseFirestore.getInstance()
//
//     override suspend fun userExists(): Boolean {
//         val user = auth.currentUser
//         return user != null
//     }
//
//     override suspend fun userProfileCreated(): Boolean {
//         val user = auth.currentUser
//         user?.let {
//             val userDoc = firestore.collection("users").document(it.uid)
//             val snapshot = userDoc.get().await() // Use await to wait for the document to be retrieved
//             return snapshot.exists() // Returns true if the document exists in Firestore
//         }
//         return false
//     }
// }

