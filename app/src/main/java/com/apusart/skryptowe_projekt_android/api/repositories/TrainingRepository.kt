package com.apusart.skryptowe_projekt_android.api.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.apusart.skryptowe_projekt_android.api.local_data_source.services.TrainingLocalService
import com.apusart.skryptowe_projekt_android.api.local_data_source.services.UserLocalService
import com.apusart.skryptowe_projekt_android.api.models.Resource
import com.apusart.skryptowe_projekt_android.api.models.Training
import com.apusart.skryptowe_projekt_android.api.models.TrainingForList
import com.apusart.skryptowe_projekt_android.api.remote_data_source.TrainingRemoteService
import javax.inject.Inject

class TrainingRepository @Inject constructor(
    private val trainingLocalService: TrainingLocalService,
    private val trainingRemoteService: TrainingRemoteService,
    private val userLocalService: UserLocalService
) {
    fun getAllTrainings(): LiveData<Resource<List<TrainingForList>>> {
        return liveData {
            val currentUser = userLocalService.getCurrentUser()
            if (currentUser.status == Resource.Status.SUCCESS)
                emit(trainingRemoteService.getAllTrainings(currentUser.data?.session_id))
        }
    }

    suspend fun getTrainingById(training_id: Int): Resource<Training> {
        val currentUser = userLocalService.getCurrentUser()

        return trainingRemoteService.getTrainingById(training_id, currentUser.data?.session_id)
    }
}