package com.life.plus.tv.data.data_source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.life.plus.tv.domain.model.UserInfo

@Entity(tableName = "user_data")
data class UserEntity(
    @PrimaryKey
    val userName: String = "",
    val name: String = "",
    val phone: String = "",
    val password: String = "",
    val isLogin: Boolean = false
){
    fun toUserInfo()=
        UserInfo(
            userName = userName,
            name = name,
            password = password,
            phone = phone
        )
}