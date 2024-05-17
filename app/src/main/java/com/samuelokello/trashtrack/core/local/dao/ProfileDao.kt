package com.samuelokello.trashtrack.core.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.samuelokello.trashtrack.core.local.entity.Profile

@Dao
interface ProfileDao {
    @Insert
    suspend fun insert(profile: Profile)

    @Query("SELECT * FROM Profile LIMIT 1")
    suspend fun getProfile(): Profile?
}