package com.apusart.skryptowe_projekt_android.api.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.apusart.skryptowe_projekt_android.api.local_data_source.services.UserLocalService
import com.apusart.skryptowe_projekt_android.api.models.Resource
import com.apusart.skryptowe_projekt_android.api.models.User
import com.apusart.skryptowe_projekt_android.api.remote_data_source.UserRemoteService
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userRemoteService: UserRemoteService,
    private val userLocalService: UserLocalService
) {
    suspend fun uploadPhoto(filePath: String) {
        userRemoteService.uploadPhoto(filePath)
    }

    suspend fun registerUser(
        nick: String,
        name: String,
        surname: String,
        password: String
    ): Resource<User> {
        val userResource = userRemoteService.registerUser(nick, name, surname, password)

        if (userResource.status == Resource.Status.SUCCESS)
            userLocalService.saveUser(userResource.data!!)

        return userResource
    }

    suspend fun loginUser(nick: String, password: String): Resource<User> {
        val userResource = userRemoteService.loginUser(nick, password)

        if (userResource.status == Resource.Status.SUCCESS)
            userLocalService.saveUser(userResource.data!!)

        return userResource
    }

    fun getCurrentUser(): LiveData<Resource<User>> {
        return liveData {
            emit(Resource.pending())

            emit(userLocalService.getCurrentUser())
        }
    }

    fun getUser(): LiveData<Resource<User>> {
        return liveData {
            emit(Resource.pending())
            val localUser = userLocalService.getCurrentUser()
            emit(localUser)
            if (localUser.status == Resource.Status.SUCCESS)
                emit(userRemoteService.getUserById(localUser.data!!.user_id))
        }
    }

    suspend fun logout(): Resource<Boolean> {
        val user = userLocalService.logoutUser()

        if (user.status == Resource.Status.SUCCESS) {
            val sessionId = user.data!!.session_id ?: return Resource.error()
            return userRemoteService.logoutUser(sessionId)
        }

        return Resource.error("Cannot logout user")
    }
}