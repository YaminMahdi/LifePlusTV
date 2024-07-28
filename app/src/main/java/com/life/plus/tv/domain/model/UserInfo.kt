package com.life.plus.tv.domain.model

import com.life.plus.tv.data.data_source.local.entity.UserEntity

data class UserInfo(
    val userName: String = "",
    val name: String = "",
    val phone: String = "",
    val password: String = "",
){
    fun toUserEntity()=
        UserEntity(
            userName = userName,
            name = name,
            phone = phone,
            password = password
        )
}
