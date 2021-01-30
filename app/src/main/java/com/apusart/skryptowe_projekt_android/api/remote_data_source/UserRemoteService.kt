package com.apusart.skryptowe_projekt_android.api.remote_data_source

import android.content.ContentResolver
import android.net.Uri
import android.util.Log
import com.apusart.skryptowe_projekt_android.api.models.*
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull

import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

class UserRemoteService @Inject constructor(
    private val service: IGymRemoteService
) {
    suspend fun registerUser(
        nick: String,
        name: String,
        surname: String,
        password: String
    ): Resource<User> {
        return performRequest { service.registerUser(UserRegisterRequest(nick, name, surname, password)) }
    }

    suspend fun loginUser(nick: String, password: String): Resource<User> {
        return performRequest { service.loginUser(UserLoginRequest(nick, password)) }
    }

    suspend fun getUserById(user_id: Int): Resource<User> {
        return performRequest { service.getUserById(user_id) }
    }

    suspend fun logoutUser(session_id: String): Resource<Boolean> {
        val response = service.logoutUser(SessionIdRequest(session_id))


        val errorBody = parseErrorBody(response.errorBody()?.string())

        if (!response.isSuccessful)
            return Resource.error(errorBody.message)

        return Resource.success(true)
    }

    suspend fun deleteUser(user_id: Int, session_id: String): Resource<Boolean> {
        val response = service.deleteUser(user_id, session_id)


        val errorBody = parseErrorBody(response.errorBody()?.string())

        if (!response.isSuccessful)
            return Resource.error(errorBody.message)

        return Resource.success(true)
    }
}