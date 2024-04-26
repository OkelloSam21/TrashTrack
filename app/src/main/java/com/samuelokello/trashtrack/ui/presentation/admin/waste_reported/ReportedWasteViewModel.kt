package com.samuelokello.trashtrack.ui.presentation.admin.waste_reported

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

class ReportedWasteViewModel: ViewModel() {
    private val _reports = MutableStateFlow<List<Report>>(emptyList())
    val reports: StateFlow<List<Report>> = _reports

    // Add a loading state
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        val database = Firebase.database.reference
        val myRef = database.child("reported_waste")

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val reports = mutableListOf<Report>()
                dataSnapshot.children.forEach { child ->
                    val report = child.getValue(Report::class.java)
                    reports.add(report!!)
                }
                _reports.value = reports
                _isLoading.value = false // Set loading to false after fetching data
                Log.d(TAG, "Value is: $reports")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
                _isLoading.value = false // Set loading to false even if there's an error
            }
        })
    }
}
//admin@trashtrack.com