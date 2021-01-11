package com.apusart.skryptowe_projekt_android.api.remote_data_source

import com.apusart.skryptowe_projekt_android.api.models.*
import com.apusart.skryptowe_projekt_android.ui.logged.search.Filters

import javax.inject.Inject

class TrainingRemoteService @Inject constructor(
    private val service: IGymRemoteService
) {
    suspend fun getAllTrainings(session_id: String? = null): Resource<List<TrainingForList>> {
        return performRequest { service.getAllTrainings(session_id) }
    }

    suspend fun getTrainingById(training_id: Int, session_id: String?): Resource<Training> {
        return performRequest { service.getTrainingById(training_id, session_id) }
    }

    suspend fun createTraining(training: TrainingRawBody, session_id: String?): Resource<Training> {
        return performRequest { service.createTraining(training, session_id) }
    }

    suspend fun deleteTrainingById(training_id: Int, session_id: String?): Resource<Int> {
        return performRequest { service.deleteTrainingById(training_id, session_id) }
    }

    suspend fun getFilteredTrainings(
        filters: Filters?,
        session_id: String?
    ): Resource<List<TrainingForList>> {
        return performRequest {
            service.getTrainingByFilters(
                filters?.trainingName,
                filters?.trainingCaloriesMin,
                filters?.trainingCaloriesMax,
                filters?.trainingType,
                session_id
            )
        }
    }

    suspend fun getUserTrainings(
        user_id: Int,
        session_id: String?
    ): Resource<List<TrainingForList>> {
        return performRequest { service.getUserTrainings(user_id, session_id) }
    }
}