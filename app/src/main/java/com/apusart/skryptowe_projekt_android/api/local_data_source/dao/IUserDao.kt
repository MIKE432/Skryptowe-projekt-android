package com.apusart.skryptowe_projekt_android.api.local_data_source.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.apusart.skryptowe_projekt_android.api.models.User

@Dao
interface IUserDao {

    @Query("SELECT * FROM User LIMIT 1")
    suspend fun getCurrentUser(): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUser(user: User)

    @Query("DELETE FROM User WHERE user_id = :user_id")
    suspend fun deleteUser(user_id: Int): Int

    @Query("DELETE FROM User")
    suspend fun deleteAll(): Int
}