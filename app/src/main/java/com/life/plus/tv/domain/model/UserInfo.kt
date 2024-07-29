package com.life.plus.tv.domain.model

import android.os.Parcelable
import com.life.plus.tv.data.data_source.local.entity.UserEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserInfo(
    val userName: String = "",
    val name: String = "",
    val phone: String = "",
    val password: String = "",
): Parcelable{
    fun toUserEntity()=
        UserEntity(
            userName = userName,
            name = name,
            phone = phone,
            password = password
        )
}
