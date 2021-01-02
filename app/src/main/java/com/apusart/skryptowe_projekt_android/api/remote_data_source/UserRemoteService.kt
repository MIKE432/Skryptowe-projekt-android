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
    suspend fun uploadPhoto(filePath: String) {
        val file = File(filePath)
        val requestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)

        val body = MultipartBody.Part.createFormData("avatar", file.name, requestFile)
        service.put(8, "tO7XMlfY9KFrj6KNZ33Vgtr3A", body)
    }

    suspend fun registerUser(
        nick: String,
        name: String,
        surname: String,
        password: String
    ): Resource<User> {
        val response = service.registerUser(UserRegisterRequest(nick, name, surname, password))

        val body = response.body()
        val errorBody = parseErrorBody(response.errorBody()?.string())

        if (!response.isSuccessful)
            return Resource.error(errorBody.message)


        return Resource.success(
            User(
                body!!.user_id,
                body.nick,
                body.name,
                body.surname,
                body.avatar,
                body.session_id
            )
        )
    }

    suspend fun loginUser(nick: String, password: String): Resource<User> {
        val response = service.loginUser(UserLoginRequest(nick, password))

        val body = response.body()
        val errorBody = parseErrorBody(response.errorBody()?.string())

        if (!response.isSuccessful)
            return Resource.error(errorBody.message)

        return Resource.success(
            User(
                body!!.user_id,
                body.nick,
                body.name,
                body.surname,
                body.avatar,
                body.session_id
            )
        )
    }

    suspend fun getUserById(user_id: Int): Resource<User> {
        val response = service.getUserById(user_id)

        val body = response.body()
        val errorBody = parseErrorBody(response.errorBody()?.string())

        if (!response.isSuccessful)
            return Resource.error(errorBody.message)

        return Resource.success(
            User(
                body!!.user_id,
                body.nick,
                body.name,
                body.surname,
                body.avatar,
                body.session_id
            )
        )
    }

    suspend fun logoutUser(session_id: String): Resource<Boolean> {
        val response = service.logoutUser(SessionIdRequest(session_id))

        val body = response.body()
        val errorBody = parseErrorBody(response.errorBody()?.string())

        if (!response.isSuccessful)
            return Resource.error(errorBody.message)

        return Resource.success(true)
    }
}