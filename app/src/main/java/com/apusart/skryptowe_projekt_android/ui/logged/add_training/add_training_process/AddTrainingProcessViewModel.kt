package com.apusart.skryptowe_projekt_android.ui.logged.add_training.add_training_process

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.apusart.skryptowe_projekt_android.api.models.SeriesAddRequest
import javax.inject.Inject

class AddTrainingProcessViewModel @Inject constructor(): ViewModel() {
    val step = MutableLiveData(0)
    val max = MutableLiveData(3)
    val trainingType = MutableLiveData<String>()

    val trainingName = MutableLiveData<String>()
    val trainingAbout = MutableLiveData<String>()
    val trainingIsPublic = MutableLiveData(true)
    val series = MutableLiveData(listOf<SeriesAddRequest>())
    fun increment() {
        step.value = step.value?.plus(1)
    }

    fun decrement() {
        step.value = step.value?.minus(1)
    }

    fun setTrainingType(type: String?) {
        if (type != trainingType.value)
            trainingType.value = type
    }

    fun addSeries(s: SeriesAddRequest) {
        series.value = series.value?.plus(s)
    }
}