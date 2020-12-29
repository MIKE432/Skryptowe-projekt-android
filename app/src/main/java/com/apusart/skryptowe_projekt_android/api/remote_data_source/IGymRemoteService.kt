package com.apusart.skryptowe_projekt_android.api.remote_data_source

import okhttp3.MultipartBody
import retrofit2.http.*

interface IGymRemoteService {
    @Multipart
    @PUT("api/users/{user_id}")
    suspend fun put(@Path("user_id") user_id: Int, @Query("session_id") session_id: String, @Part avatar: MultipartBody.Part)
}