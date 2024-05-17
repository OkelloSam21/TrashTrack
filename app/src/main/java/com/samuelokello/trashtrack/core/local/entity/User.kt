package com.samuelokello.trashtrack.core.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class User(
    @PrimaryKey(autoGenerate = true)val id: Int? = null,
    val email: String,
)
