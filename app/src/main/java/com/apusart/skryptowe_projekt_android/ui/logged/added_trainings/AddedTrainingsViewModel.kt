package com.apusart.skryptowe_projekt_android.ui.logged.added_trainings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apusart.skryptowe_projekt_android.api.models.Resource
import com.apusart.skryptowe_projekt_android.api.models.TrainingForList
import com.apusart.skryptowe_projekt_android.api.repositories.TrainingRepository
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class AddedTrainingsViewModel @Inject constructor(private val trainingRepository: TrainingRepository): ViewModel() {
    val trainings = trainingRepository.getUserTrainings()
    val deletedTrainings = MutableLiveData(listOf<Resource<Int>>())

    fun deleteTraining(trainingId: Int) {
        viewModelScope.launch {
            deletedTrainings.value = deletedTrainings.value?.plus(trainingRepository.deleteTrainingById(trainingId))
        }
    }
}