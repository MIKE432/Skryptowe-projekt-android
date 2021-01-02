package com.apusart.skryptowe_projekt_android.api.local_data_source

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.apusart.skryptowe_projekt_android.api.models.User
import com.apusart.skryptowe_projekt_android.api.local_data_source.dao.IUserDao

@Database(entities = [User::class], version = 1)
abstract class GymDatabase: RoomDatabase() {
    abstract fun userDao(): IUserDao

    companion object {
        private lateinit var applicationContext: Context

        val db by lazy {
            databaseBuilder(applicationContext, GymDatabase::class.java, "gym.db")
                .build()
        }

        fun initialize(appContext: Context) {
            applicationContext = appContext
        }
    }
}