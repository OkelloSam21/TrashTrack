package com.samuelokello.trashtrack.util

import androidx.compose.ui.platform.LocalContext
import androidx.room.Room
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.database
import com.samuelokello.trashtrack.data.local.AppDatabase

object Utils {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val database = Firebase.database

    // local

}