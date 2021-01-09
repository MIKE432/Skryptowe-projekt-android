package com.apusart.skryptowe_projekt_android.ui.logged.added_trainings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.apusart.skryptowe_projekt_android.api.models.TrainingForList
import com.apusart.skryptowe_projekt_android.api.repositories.TrainingRepository
import retrofit2.Response
import javax.inject.Inject

class AddedTrainingsViewModel @Inject constructor(trainingRepository: TrainingRepository): ViewModel() {
    val trainings = trainingRepository.getUserTrainings()
}