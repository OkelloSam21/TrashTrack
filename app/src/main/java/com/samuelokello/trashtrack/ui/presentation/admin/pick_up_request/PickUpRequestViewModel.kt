package com.samuelokello.trashtrack.ui.presentation.admin.pick_up_request

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PickUpRequestViewModel: ViewModel() {
    private val _requests = MutableStateFlow<List<Request>>(emptyList())
    val requests: StateFlow<List<Request>> = _requests

    init{
        val database = Firebase.database.reference
// Fetch requests
        val requestsRef = database.child("requested_waste")
        requestsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                _requests.value = dataSnapshot.children.map { it.getValue(Request::class.java)!! }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }
}