package com.apusart.skryptowe_projekt_android.api.repositories

import android.net.Uri
import com.apusart.skryptowe_projekt_android.api.local_data_source.services.UserLocalService
import com.apusart.skryptowe_projekt_android.api.remote_data_source.UserRemoteService
import javax.inject.Inject

class UserRepository @Inject constructor(
    val userRemoteService: UserRemoteService,
    val userLocalService: UserLocalService
) {
    suspend fun uploadPhoto(filePath: String) {
        userRemoteService.uploadPhoto(filePath)
    }
}