package com.samuelokello.trashtrack.core.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Profile(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val email: String,
    val location: String,
    val phoneNumber: String,
)
