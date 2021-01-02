package com.apusart.skryptowe_projekt_android.api.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.GsonBuilder
import kotlinx.android.parcel.Parcelize

class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    enum class Status {
        SUCCESS,
        PENDING,
        ERROR
    }

    companion object {
        fun<T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun<T> pending(message: String? = null): Resource<T> {
            return Resource(Status.PENDING, null, message)
        }

        fun<T> error(message: String? = "", data: T? = null): Resource<T> {
            return Resource(Status.ERROR, data, message)
        }
    }
}

fun <T> handleResource(
    res: Resource<T>,
    onSuccess: (data: T?) -> Unit = { _ -> },
    onPending: (data: T?) -> Unit = { _ -> },
    onError: (message: String?, data: T?) -> Unit = { _, _ -> }
) {
    when(res.status) {
        Resource.Status.SUCCESS -> onSuccess(res.data)
        Resource.Status.PENDING -> onPending(res.data)
        Resource.Status.ERROR -> onError(res.message, res.data)
    }
}

fun parseErrorBody(errorBody: String?): ErrorBody {
    if (errorBody == null)
        return ErrorBody()

    val gson = GsonBuilder().create()
    return gson.fromJson(errorBody, ErrorBody::class.java)
}

data class ErrorBody(
    val code: Int? = -1,
    val message: String? = ""
)

data class CodeAndStatusResponse(
    val code: Int?,
    val message: String?
)

data class TrainingType(
    val type: String = "OTHER",
    val about: String = ""
)
