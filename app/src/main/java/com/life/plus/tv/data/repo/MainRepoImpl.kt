package com.life.plus.tv.data.repo

import com.life.plus.tv.data.data_source.local.room.UserDao
import com.life.plus.tv.data.data_source.remote.dto.SearchInfoDto
import com.life.plus.tv.domain.ErrorType
import com.life.plus.tv.domain.RequestState
import com.life.plus.tv.domain.model.ShowInfo
import com.life.plus.tv.domain.model.UserInfo
import com.life.plus.tv.domain.repo.MainRepo
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.isSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainRepoImpl @Inject constructor(
    private val ktorClient: HttpClient,
    private val userDao: UserDao
) : MainRepo {
    override suspend fun login(userName: String, password: String): RequestState<UserInfo> {
        return withContext(Dispatchers.IO) {
            val user = userDao.getUser(userName)
            if (user == null)
                RequestState.Error("User doesn't exist", ErrorType.InvalidUserName)
            else if (user.password != password)
                RequestState.Error("Password didn't matched", ErrorType.InvalidPass)
            else {
                userDao.updateLoginStatus(userName, true)
                RequestState.Success(user.toUserInfo())
            }
        }
    }

    override suspend fun register(userInfo: UserInfo): RequestState<String> =
        withContext(Dispatchers.IO) {
            if (userDao.addUser(userInfo.toUserEntity()) == -1L)
                RequestState.Error("User Name already taken", ErrorType.InvalidUserName)
            else
                RequestState.Success("Successful, try to login")
        }


    override suspend fun getLoggedInUser(): Flow<UserInfo?> =
        withContext(Dispatchers.IO) { userDao.getLoggedInUser().map { it?.toUserInfo() } }

    override suspend fun getUser(userName: String): UserInfo? =
        withContext(Dispatchers.IO) { userDao.getUser(userName)?.toUserInfo() }

    override suspend fun updateLoginStatus(userName: String, isLogin: Boolean) =
        withContext(Dispatchers.IO) { userDao.updateLoginStatus(userName, isLogin) }

    override suspend fun getShows(query: String): RequestState<List<ShowInfo>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = ktorClient.get("search/shows?q=$query")
                if (response.status.isSuccess()) {
                    val body = response
                        .body<List<SearchInfoDto>>()
                        .map { it.toShowInfo() }
                    RequestState.Success(body)
                } else
                    RequestState.Error(response.status.description)
            } catch (e: Exception) {
                RequestState.Error(e.message.toString())
            }
        }
    }


}