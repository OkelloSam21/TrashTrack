package com.samuelokello.trashtrack.core.domain.repositories

import com.samuelokello.trashtrack.core.local.entity.User
import com.samuelokello.trashtrack.core.local.dao.UserDao

class RoomUserRepository(private val userDao: UserDao) : UserRepository {
    override suspend fun userExists(): Boolean {
        val user = userDao.getUser()
        return user != null
    }

    override suspend fun userProfileCreated(): Boolean {
        val user = userDao.getUser()
        val profile = user?.let { userDao.getProfile() }
        return profile != null
    }

    override suspend fun insertUser(user: User) {
        userDao.insert(user)
    }
}

interface UserRepository {
    suspend fun userExists(): Boolean
    suspend fun userProfileCreated(): Boolean
    suspend fun insertUser(user: User)
}