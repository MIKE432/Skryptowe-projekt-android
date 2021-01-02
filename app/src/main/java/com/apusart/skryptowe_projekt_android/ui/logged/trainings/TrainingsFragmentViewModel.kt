package com.apusart.skryptowe_projekt_android.ui.logged.trainings

import com.apusart.skryptowe_projekt_android.api.repositories.TrainingRepository
import javax.inject.Inject

class TrainingsFragmentViewModel @Inject constructor(private val trainingRepository: TrainingRepository) {
    val trainings = trainingRepository.getAllTrainings()
}