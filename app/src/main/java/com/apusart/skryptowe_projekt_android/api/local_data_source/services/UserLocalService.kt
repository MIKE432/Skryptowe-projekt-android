package com.apusart.skryptowe_projekt_android.api.local_data_source.services

import com.apusart.skryptowe_projekt_android.api.local_data_source.GymDatabase
import com.apusart.skryptowe_projekt_android.api.models.Resource
import com.apusart.skryptowe_projekt_android.api.models.User
import javax.inject.Inject

class UserLocalService @Inject constructor(
    private val db: GymDatabase
) {
    suspend fun saveUser(user: User) {
        db.userDao().deleteAll()
        db.userDao().saveUser(user)
    }

    suspend fun getCurrentUser(): Resource<User> {
        val user = db.userDao().getCurrentUser() ?: return Resource.error("No user is logged in")

        return Resource.success(user)
    }

    suspend fun logoutUser(): Resource<User> {
        val user = db.userDao().getCurrentUser() ?: return Resource.error("No user is logged in")
        val deletedCount = db.userDao().deleteAll()

        if (deletedCount == 0)
            return Resource.error("No user is logged in")

        return Resource.success(user)
    }
}