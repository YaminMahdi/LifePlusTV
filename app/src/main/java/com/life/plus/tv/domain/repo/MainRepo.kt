package com.life.plus.tv.domain.repo

import com.life.plus.tv.data.data_source.local.entity.UserEntity
import com.life.plus.tv.domain.RequestState
import com.life.plus.tv.domain.model.SearchInfo

interface MainRepo {
    fun getUser(userName: String): UserEntity?
    fun getLoggedInUser(): UserEntity?
    fun updateLoginStatus(userName: String, isLogin: Boolean)
    fun addUser(product: UserEntity): Long?
    suspend fun getShows(query: String): RequestState<List<SearchInfo>>
}