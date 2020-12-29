package com.apusart.skryptowe_projekt_android.api.remote_data_source

import android.content.ContentResolver
import android.net.Uri
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull

import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

class UserRemoteService @Inject constructor(
    val service: IGymRemoteService
) {
    suspend fun uploadPhoto(filePath: String) {
        val file = File(filePath)
        val requestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)

        val body = MultipartBody.Part.createFormData("avatar", file.name, requestFile)
        service.put(8, "tO7XMlfY9KFrj6KNZ33Vgtr3A", body)

    }
}