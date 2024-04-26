package com.samuelokello.trashtrack.data.local

import androidx.room.Insert
import androidx.room.Query


@androidx.room.Dao
interface UserDao {
    @Insert
    fun insert(user: User)

    @Query("SELECT * FROM User LIMIT 1")
    fun getUser(): User?

    @Query("SELECT * FROM Profile LIMIT 1")
    fun getProfile(): Profile?
}