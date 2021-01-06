package com.apusart.skryptowe_projekt_android.api.remote_data_source

import com.apusart.skryptowe_projekt_android.api.models.*
import com.apusart.skryptowe_projekt_android.ui.logged.search.Filters

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

    suspend fun createTraining(training: TrainingRawBody, session_id: String?): Resource<Training> {
        val response = service.createTraining(training, session_id)
        val body = response.body()
        val errorBody = parseErrorBody(response.errorBody()?.string())

        if (!response.isSuccessful)
            return Resource.error(errorBody.message)

        return Resource.success(body!!)
    }

    suspend fun deleteTrainingById(training_id: Int, session_id: String?): Resource<Boolean> {
        val response = service.deleteTrainingById(training_id, session_id)
        val errorBody = parseErrorBody(response.errorBody()?.string())

        if (!response.isSuccessful)
            return Resource.error(errorBody.message)

        return Resource.success(true)
    }

    suspend fun getFilteredTrainings(
        filters: Filters?,
        session_id: String?
    ): Resource<List<TrainingForList>> {
        val response = service.getTrainingByFilters(
            filters?.trainingName,
            filters?.trainingCaloriesMin,
            filters?.trainingCaloriesMax,
            filters?.trainingType,
            session_id
        )
        val body = response.body()
        val errorBody = parseErrorBody(response.errorBody()?.string())

        if (!response.isSuccessful)
            return Resource.error(errorBody.message)

        return Resource.success(body!!)
    }
}