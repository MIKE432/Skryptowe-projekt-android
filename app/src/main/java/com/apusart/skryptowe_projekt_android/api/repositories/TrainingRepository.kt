package com.apusart.skryptowe_projekt_android.api.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.apusart.skryptowe_projekt_android.api.local_data_source.services.TrainingLocalService
import com.apusart.skryptowe_projekt_android.api.local_data_source.services.UserLocalService
import com.apusart.skryptowe_projekt_android.api.models.*
import com.apusart.skryptowe_projekt_android.api.remote_data_source.ExerciseRemoteService
import com.apusart.skryptowe_projekt_android.api.remote_data_source.SeriesRemoteService
import com.apusart.skryptowe_projekt_android.api.remote_data_source.TrainingRemoteService
import com.apusart.skryptowe_projekt_android.ui.logged.search.Filters
import javax.inject.Inject

class TrainingRepository @Inject constructor(
    private val trainingLocalService: TrainingLocalService,
    private val trainingRemoteService: TrainingRemoteService,
    private val userLocalService: UserLocalService,
    private val seriesRemoteService: SeriesRemoteService,
    private val exerciseRemoteService: ExerciseRemoteService
) {
    fun getAllTrainings(): LiveData<Resource<List<TrainingForList>>> {
        return liveData {
            val currentUser = userLocalService.getCurrentUser()
            if (currentUser.status == Resource.Status.SUCCESS)
                emit(trainingRemoteService.getAllTrainings(currentUser.data?.session_id))
        }
    }

    fun getUserTrainings(): LiveData<Resource<List<TrainingForList>>> {
        return liveData {
            val currentUser = userLocalService.getCurrentUser()
            if (currentUser.status == Resource.Status.SUCCESS)
                emit(trainingRemoteService.getUserTrainings(currentUser.data!!.user_id, currentUser.data.session_id))
        }
    }

    suspend fun getTrainingById(training_id: Int): Resource<Training> {
        val currentUser = userLocalService.getCurrentUser()
        return trainingRemoteService.getTrainingById(training_id, currentUser.data?.session_id)
    }

    suspend fun createTraining(trainingRawBody: TrainingRawBody, series: List<SeriesAddRequest>): Resource<Training> {
        val currentUser = userLocalService.getCurrentUser()
        val sessionId = currentUser.data?.session_id
        val training = trainingRemoteService.createTraining(trainingRawBody, sessionId)

        if (training.status != Resource.Status.SUCCESS)
            return Resource.error(training.message)

        series.forEach {
            val _series = seriesRemoteService.createSeries(SeriesRawBody(it.iteration, it.rest_time), training.data!!.training_id, sessionId)

            if (_series.status != Resource.Status.SUCCESS) {
                trainingRemoteService.deleteTrainingById(training.data.training_id, sessionId)
                return Resource.error(_series.message)
            }

            it.exercises.forEach { exercise ->
                val _exercise = exerciseRemoteService.createExercise(exercise, _series.data!!.series_id, sessionId)

                if (_exercise.status != Resource.Status.SUCCESS){
                    trainingRemoteService.deleteTrainingById(training.data.training_id, sessionId)
                    return Resource.error(_exercise.message)
                }
            }
        }

        return Resource.success(training.data!!)
    }

    suspend fun deleteTrainingById(trainingId: Int): Resource<Int> {
        val currentUser = userLocalService.getCurrentUser()
        val sessionId = currentUser.data?.session_id

        return trainingRemoteService.deleteTrainingById(trainingId, sessionId)
    }

    suspend fun getFilteredTrainings(filters: Filters?): Resource<List<TrainingForList>> {
        val currentUser = userLocalService.getCurrentUser()
        val sessionId = currentUser.data?.session_id

        return trainingRemoteService.getFilteredTrainings(filters, sessionId)
    }
}