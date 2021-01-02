package com.apusart.skryptowe_projekt_android.api.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class UserLoginRequest(
    val nick: String,
    val password: String
)

data class UserRegisterRequest(
    val nick: String,
    val name: String,
    val surname: String,
    val password: String
)

@Entity
data class User(
    @PrimaryKey val user_id: Int,
    val nick: String,
    val name: String,
    val surname: String,
    val avatar: String?,
    val session_id: String?
)

data class UserResponse(
    val user_id: Int,
    val nick: String,
    val name: String,
    val surname: String,
    val avatar: String?,
    val session_id: String?
)

data class SessionIdRequest(
    val session_id: String
)
