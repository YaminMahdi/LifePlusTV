package com.life.plus.tv.data.data_source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.life.plus.tv.data.data_source.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM user_data WHERE userName = :userName")
    suspend fun getUser(userName: String): UserEntity?

    @Query("SELECT * FROM user_data WHERE isLogin = 1 LIMIT 1")
    fun getLoggedInUser(): Flow<UserEntity?>

    @Query("UPDATE user_data SET isLogin = :isLogin WHERE userName = :userName")
    suspend fun updateLoginStatus(userName: String, isLogin: Boolean)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(product: UserEntity): Long
}