package com.apusart.skryptowe_projekt_android.api.remote_data_source

import com.apusart.skryptowe_projekt_android.api.models.Resource
import com.apusart.skryptowe_projekt_android.api.models.Training
import com.apusart.skryptowe_projekt_android.api.models.TrainingForList
import com.apusart.skryptowe_projekt_android.api.models.parseErrorBody

import javax.inject.Inject

class TrainingRemoteService @Inject constructor(
    private val service: IGymRemoteService
) {
    suspend fun getAllTrainings(session_id: String? = null): Resource<List<TrainingForList>> {
        val response = service.getAllTrainings(session_id)

        val body = response.body()
        val errorBody = parseErrorBody(response.errorBody()?.string())

        if (!response.isSuccessful)
            return Resource.error(errorBody.message)

        return Resource.success(body!!)
    }

    suspend fun getTrainingById(training_id: Int, session_id: String?): Resource<Training> {
        val response = service.getTrainingById(training_id, session_id)
        val body = response.body()
        val errorBody = parseErrorBody(response.errorBody()?.string())

        if (!response.isSuccessful)
            return Resource.error(errorBody.message)

        return Resource.success(body!!)
    }
}