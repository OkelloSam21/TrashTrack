package com.samuelokello.trashtrack.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

import androidx.room.ForeignKey
import androidx.room.Index

@Entity()
data class User(
    @PrimaryKey(autoGenerate = true)val id: Int? = null,
    val email: String,
)
