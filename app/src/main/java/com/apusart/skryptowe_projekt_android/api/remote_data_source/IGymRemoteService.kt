package com.apusart.skryptowe_projekt_android.api.remote_data_source

import com.apusart.skryptowe_projekt_android.api.models.*
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface IGymRemoteService {
    @Multipart
    @PUT("api/users/{user_id}")
    suspend fun put(@Path("user_id") user_id: Int, @Query("session_id") session_id: String, @Part avatar: MultipartBody.Part)

    @POST("api/user/register")
    suspend fun registerUser(@Body registerBody: UserRegisterRequest): Response<UserResponse>

    @POST("api/user/login")
    suspend fun loginUser(@Body registerBody: UserLoginRequest): Response<UserResponse>

    @GET("api/users/{user_id}")
    suspend fun getUserById(@Path("user_id") user_id: Int): Response<UserResponse>

    @PUT("api/user/logout")
    suspend fun logoutUser(@Body logoutBody: SessionIdRequest): Response<CodeAndStatusResponse>

    @GET("api/trainings")
    suspend fun getAllTrainings(@Query("session_id") session_id: String?): Response<List<TrainingForList>>

    @GET("api/trainings/{training_id}")
    suspend fun getTrainingById(@Path("training_id") training_id: Int, @Query("session_id") session_id: String? = null): Response<Training>
}