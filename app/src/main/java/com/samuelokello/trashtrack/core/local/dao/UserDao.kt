package com.samuelokello.trashtrack.core.local.dao

import androidx.room.Insert
import androidx.room.Query
import com.samuelokello.trashtrack.core.local.entity.Profile
import com.samuelokello.trashtrack.core.local.entity.User


@androidx.room.Dao
interface UserDao {
    @Insert
    fun insert(user: User)

    @Query("SELECT * FROM User LIMIT 1")
    fun getUser(): User?

    @Query("SELECT * FROM Profile LIMIT 1")
    fun getProfile(): Profile?
}