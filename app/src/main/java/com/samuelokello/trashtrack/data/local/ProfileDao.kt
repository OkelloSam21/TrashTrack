package com.samuelokello.trashtrack.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProfileDao {
    @Insert
    suspend fun insert(profile: Profile)

    @Query("SELECT * FROM Profile LIMIT 1")
    suspend fun getProfile(): Profile?
}