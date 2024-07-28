package com.life.plus.tv.data.repo

import com.life.plus.tv.data.data_source.local.entity.UserEntity
import com.life.plus.tv.data.data_source.local.room.UserDao
import com.life.plus.tv.data.data_source.remote.dto.SearchInfoDto
import com.life.plus.tv.domain.RequestState
import com.life.plus.tv.domain.model.SearchInfo
import com.life.plus.tv.domain.repo.MainRepo
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.isSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainRepoImpl @Inject constructor(
    private val ktorClient: HttpClient,
    private val userDao: UserDao
) : MainRepo {
    override fun getUser(userName: String) = userDao.getUser(userName)
    override fun getLoggedInUser() = userDao.getLoggedInUser()
    override fun updateLoginStatus(userName: String, isLogin: Boolean) = userDao.updateLoginStatus(userName, isLogin)
    override fun addUser(product: UserEntity) = userDao.addUser(product)

    override suspend fun getShows(query: String): RequestState<List<SearchInfo>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = ktorClient.get("search/shows?q=$query")
                if (response.status.isSuccess()) {
                    val body = response
                        .body<List<SearchInfoDto>>()
                        .map { it.toSearchInfo() }
                    RequestState.Success(body)
                } else
                    RequestState.Error(response.status.description)
            } catch (e: Exception) {
                RequestState.Error(e.message.toString())
            }
        }
    }


}