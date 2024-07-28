package com.life.plus.tv.domain.repo

import com.life.plus.tv.domain.RequestState
import com.life.plus.tv.domain.model.SearchInfo
import com.life.plus.tv.domain.model.UserInfo
import kotlinx.coroutines.flow.Flow

interface MainRepo {
    suspend fun login(userName: String, password: String): RequestState<UserInfo>
    suspend fun register(userInfo: UserInfo): RequestState<String>
    suspend fun getLoggedInUser(): Flow<UserInfo?>
    suspend fun updateLoginStatus(userName: String, isLogin: Boolean)
    suspend fun getShows(query: String): RequestState<List<SearchInfo>>
}