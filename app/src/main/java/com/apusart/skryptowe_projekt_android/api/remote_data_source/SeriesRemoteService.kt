package com.apusart.skryptowe_projekt_android.api.remote_data_source

import com.apusart.skryptowe_projekt_android.api.models.*
import javax.inject.Inject

class SeriesRemoteService @Inject constructor(
    private val service: IGymRemoteService
) {
    suspend fun createSeries(
        series: SeriesRawBody,
        training_id: Int,
        session_id: String? = null
    ): Resource<Series> {
        val response = service.createSeries(training_id, series, session_id)

        val body = response.body()
        val errorBody = parseErrorBody(response.errorBody()?.string())

        if (!response.isSuccessful)
            return Resource.error(errorBody.message)

        return Resource.success(body!!)
    }
}