package com.samuelokello.trashtrack.ui.presentation.admin.home

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

class AdminViewModel: ViewModel() {
    private val _reportsCount = MutableStateFlow(0) // initial value: 0
    val reportsCount: StateFlow<Int> = _reportsCount

    private val _requestsCount = MutableStateFlow(0) // initial value: 0
    val requestsCount: StateFlow<Int> = _requestsCount

    init {
        val database = Firebase.database.reference
        val reportRef = database.child("reported_waste")
        val requestRef = database.child("requested_waste")


        reportRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                _reportsCount.value = dataSnapshot.childrenCount.toInt()
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })

        requestRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                _requestsCount.value += dataSnapshot.childrenCount.toInt()
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }
}