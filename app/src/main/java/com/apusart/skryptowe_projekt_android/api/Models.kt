package com.apusart.skryptowe_projekt_android.api

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val user_id: Int,
    @ColumnInfo(name = "title") val name: String
)

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

        fun<T> error(message: String?, data: T? = null): Resource<T> {
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