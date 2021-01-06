package com.apusart.skryptowe_projekt_android.ui.logged.search

import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apusart.skryptowe_projekt_android.api.local_data_source.GymDatabase
import com.apusart.skryptowe_projekt_android.api.local_data_source.services.TrainingLocalService
import com.apusart.skryptowe_projekt_android.api.local_data_source.services.UserLocalService
import com.apusart.skryptowe_projekt_android.api.models.Resource
import com.apusart.skryptowe_projekt_android.api.models.TrainingForList
import com.apusart.skryptowe_projekt_android.api.remote_data_source.ExerciseRemoteService
import com.apusart.skryptowe_projekt_android.api.remote_data_source.IGymRemoteService
import com.apusart.skryptowe_projekt_android.api.remote_data_source.SeriesRemoteService
import com.apusart.skryptowe_projekt_android.api.remote_data_source.TrainingRemoteService
import com.apusart.skryptowe_projekt_android.api.repositories.TrainingRepository
import com.apusart.skryptowe_projekt_android.tools.Defaults
import com.apusart.skryptowe_projekt_android.tools.TrainingTypes
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import javax.inject.Inject

class Filters(
    val trainingName: String? = null,
    val trainingCaloriesMin: Int? = null,
    val trainingCaloriesMax: Int? = null,
    val trainingType: String? = null
)

class SearchViewModel @Inject constructor(): ViewModel() {

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


    val filters = MutableLiveData(Filters())
    val trainings = MutableLiveData<Resource<List<TrainingForList>>>()
    fun commitFilters(f: Filters) {
        filters.value = f
    }

    fun getTrainings() {
        viewModelScope.launch {
            try {
                trainings.value = trainingRepository.getFilteredTrainings(filters.value)
            } catch (e: Exception) {
                trainings.value = Resource.error(e.message)
            }
        }
    }
}