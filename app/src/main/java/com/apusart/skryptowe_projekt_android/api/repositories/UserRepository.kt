package com.apusart.skryptowe_projekt_android.api.repositories

import com.apusart.skryptowe_projekt_android.api.local_data_source.services.UserLocalService
import com.apusart.skryptowe_projekt_android.api.remote_data_source.UserRemoteService
import javax.inject.Inject

class UserRepository @Inject constructor(
    userRemoteService: UserRemoteService,
    userLocalService: UserLocalService
) {

}