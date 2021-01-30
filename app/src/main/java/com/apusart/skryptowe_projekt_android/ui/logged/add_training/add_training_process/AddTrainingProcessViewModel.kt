package com.apusart.skryptowe_projekt_android.ui.logged.add_training.add_training_process

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apusart.skryptowe_projekt_android.api.local_data_source.GymDatabase
import com.apusart.skryptowe_projekt_android.api.local_data_source.services.TrainingLocalService
import com.apusart.skryptowe_projekt_android.api.local_data_source.services.UserLocalService
import com.apusart.skryptowe_projekt_android.api.models.Resource
import com.apusart.skryptowe_projekt_android.api.models.SeriesAddRequest
import com.apusart.skryptowe_projekt_android.api.models.Training
import com.apusart.skryptowe_projekt_android.api.models.TrainingRawBody
import com.apusart.skryptowe_projekt_android.api.remote_data_source.ExerciseRemoteService
import com.apusart.skryptowe_projekt_android.api.remote_data_source.IGymRemoteService
import com.apusart.skryptowe_projekt_android.api.remote_data_source.SeriesRemoteService
import com.apusart.skryptowe_projekt_android.api.remote_data_source.TrainingRemoteService
import com.apusart.skryptowe_projekt_android.api.repositories.TrainingRepository
import com.apusart.skryptowe_projekt_android.tools.Defaults
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.text.FieldPosition

class AddTrainingProcessViewModel:
    ViewModel() {
    private val retrofit = Retrofit.Builder()
        .baseUrl(Defaults.baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(IGymRemoteService::class.java)

    private val trainingLocalService = TrainingLocalService(GymDatabase.db)
    private val trainingRemoteService = TrainingRemoteService(retrofit)
    private val userLocalService = UserLocalService(GymDatabase.db)
    private val seriesRemoteService = SeriesRemoteService(retrofit)
    private val exerciseRemoteService = ExerciseRemoteService(retrofit)

    private val trainingRepository = TrainingRepository(
        trainingLocalService,
        trainingRemoteService,
        userLocalService,
        seriesRemoteService,
        exerciseRemoteService
    )

    val step = MutableLiveData(0)
    val max = 3
    val trainingType = MutableLiveData<String>()

    val trainingName = MutableLiveData<String>()
    val trainingAbout = MutableLiveData("")
    val trainingIsPublic = MutableLiveData(true)
    val series = MutableLiveData(listOf<SeriesAddRequest>())
    val trainingCalories = MutableLiveData(0)
    val createdTraining = MutableLiveData<Resource<Training>>()

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
        val c = s.exercises.foldRight(0) { x, acc ->
            acc + x.exercise_calories
        }

        trainingCalories.value = trainingCalories.value?.plus(c*s.iteration)
        series.value = series.value?.plus(s)
    }

    fun removeSeries(position: Int) {
        if (series.value?.size ?: 0 > position)
        series.value = series.value?.minus(series.value!![position])
    }

    fun createTraining() {
        viewModelScope.launch {
            createdTraining.value = Resource.pending()

            createdTraining.value =
                trainingRepository.createTraining(
                    TrainingRawBody(
                        trainingName.value!!,
                        trainingAbout.value!!,
                        trainingIsPublic.value!!,
                        trainingType.value!!,
                        trainingCalories.value!!
                    ),
                    series.value!!
                )
            try {

            } catch (e: Exception) {
                createdTraining.value = Resource.error(e.message)
            }
        }
    }
}