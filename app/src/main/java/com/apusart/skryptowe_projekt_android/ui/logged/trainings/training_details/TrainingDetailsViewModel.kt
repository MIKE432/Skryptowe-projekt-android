package com.apusart.skryptowe_projekt_android.ui.logged.trainings.training_details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apusart.skryptowe_projekt_android.api.models.Resource
import com.apusart.skryptowe_projekt_android.api.models.Training
import com.apusart.skryptowe_projekt_android.api.repositories.TrainingRepository
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class TrainingDetailsViewModel @Inject constructor(private val trainingRepository: TrainingRepository): ViewModel() {
    val trainingDetails = MutableLiveData<Resource<Training>>()

    fun getTrainingById(trainingId: Int) {
        viewModelScope.launch {
            try {
                trainingDetails.value = Resource.pending()
                trainingDetails.value = trainingRepository.getTrainingById(trainingId)
            } catch (e: Exception) {
                trainingDetails.value = Resource.error(e.message)
            }
        }
    }
}