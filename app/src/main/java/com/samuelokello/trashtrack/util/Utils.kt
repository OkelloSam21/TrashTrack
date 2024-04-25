package com.samuelokello.trashtrack.util

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.database

object Utils {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val database = Firebase.database
}