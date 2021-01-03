package com.apusart.skryptowe_projekt_android.api.remote_data_source

import com.apusart.skryptowe_projekt_android.api.models.Exercise
import com.apusart.skryptowe_projekt_android.api.models.ExerciseAddRequest
import com.apusart.skryptowe_projekt_android.api.models.Resource
import com.apusart.skryptowe_projekt_android.api.models.parseErrorBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Inject
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class ExerciseRemoteService @Inject constructor(
    private val service: IGymRemoteService
) {
    suspend fun createExercise(
        exercise: ExerciseAddRequest,
        series_id: Int,
        session_id: String? = null
    ): Resource<Exercise> {

        val file = if (exercise.photo != null) File(exercise.photo) else null
        val photo = file?.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val photoRequest = if (photo != null) MultipartBody.Part.createFormData(
            "photo",
            file.name,
            photo
        ) else null

        val name = exercise.name.toRequestBody(MultipartBody.FORM)
        val about = exercise.about.toRequestBody(MultipartBody.FORM)
        val number = exercise.number.toString().toRequestBody(MultipartBody.FORM)
        val ytLink = (exercise.yt_link ?: "").toRequestBody(MultipartBody.FORM)
        val exerciseType = exercise.exercise_type.toRequestBody(MultipartBody.FORM)
        val exerciseCalories =
            exercise.exercise_calories.toString().toRequestBody(MultipartBody.FORM)
        val response = service.createExercise(
            series_id,
            photoRequest,
            name,
            about,
            number,
            ytLink,
            exerciseType,
            exerciseCalories,
            session_id
        )

        val body = response.body()
        val errorBody = parseErrorBody(response.errorBody()?.string())

        if (!response.isSuccessful)
            return Resource.error(errorBody.message)

        return Resource.success(body!!)
    }
}