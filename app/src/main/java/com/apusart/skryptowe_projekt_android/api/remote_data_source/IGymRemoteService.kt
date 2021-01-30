package com.apusart.skryptowe_projekt_android.api.remote_data_source

import com.apusart.skryptowe_projekt_android.api.models.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface IGymRemoteService {
    @Multipart
    @PUT("api/users/{user_id}")
    suspend fun put(
        @Path("user_id") user_id: Int,
        @Query("session_id") session_id: String,
        @Part avatar: MultipartBody.Part
    )

    @POST("api/user/register")
    suspend fun registerUser(@Body registerBody: UserRegisterRequest): Response<User>

    @POST("api/user/login")
    suspend fun loginUser(@Body registerBody: UserLoginRequest): Response<User>

    @GET("api/users/{user_id}")
    suspend fun getUserById(@Path("user_id") user_id: Int): Response<User>

    @PUT("api/user/logout")
    suspend fun logoutUser(@Body logoutBody: SessionIdRequest): Response<CodeAndStatusResponse>

    @GET("api/trainings")
    suspend fun getAllTrainings(@Query("session_id") session_id: String?): Response<List<TrainingForList>>

    @GET("api/trainings/{training_id}")
    suspend fun getTrainingById(
        @Path("training_id") training_id: Int,
        @Query("session_id") session_id: String? = null
    ): Response<Training>

    @Multipart
    @POST("api/exercises/{series_id}")
    suspend fun createExercise(
        @Path("series_id") series_id: Int,
        @Part photo: MultipartBody.Part? = null,
        @Part("name") name: RequestBody,
        @Part("about") about: RequestBody,
        @Part("number") number: RequestBody,
        @Part("yt_link") yt_link: RequestBody,
        @Part("exercise_type") exercise_type: RequestBody,
        @Part("exercise_calories") exercise_calories: RequestBody,
        @Query("session_id") session_id: String? = null
    ): Response<Exercise>

    @POST("api/series/{training_id}")
    suspend fun createSeries(
        @Path("training_id") training_id: Int,
        @Body seriesRawBody: SeriesRawBody,
        @Query("session_id") session_id: String? = null
    ): Response<Series>

    @POST("api/training")
    suspend fun createTraining(
        @Body trainingRawBody: TrainingRawBody,
        @Query("session_id") session_id: String? = null
    ): Response<Training>

    @DELETE("api/trainings/{training_id}")
    suspend fun deleteTrainingById(
        @Path("training_id") training_id: Int,
        @Query("session_id") session_id: String? = null
    ): Response<Int>

    @GET("api/trainings")
    suspend fun getTrainingByFilters(
        @Query("name") name: String?,
        @Query("calories_min") calories_min: Int?,
        @Query("calories_max") calories_max: Int?,
        @Query("type") type: String?,
        @Query("session_id") session_id: String? = ""
    ): Response<List<TrainingForList>>

    @GET("api/users/{user_id}/trainings")
    suspend fun getUserTrainings(@Path("user_id") user_id: Int, @Query("session_id") session_id: String? = null): Response<List<TrainingForList>>

    @DELETE("api/users/{user_id}")
    suspend fun deleteUser(@Path("user_id") user_id: Int, @Query("session_id") session_id: String? = null): Response<CodeAndStatusResponse>

}