package com.life.plus.tv.data.data_source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.life.plus.tv.data.data_source.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM user_data WHERE userName = :userName")
    fun getUser(userName: String): UserEntity?

    @Query("SELECT * FROM user_data WHERE isLogin = 1 LIMIT 1")
    fun getLoggedInUser(): Flow<UserEntity?>

    @Query("UPDATE user_data SET isLogin = :isLogin WHERE userName = :userName")
    fun updateLoginStatus(userName: String, isLogin: Boolean)

    @Insert
    fun addUser(product: UserEntity): Long?
}