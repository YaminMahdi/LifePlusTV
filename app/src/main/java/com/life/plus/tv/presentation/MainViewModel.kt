package com.life.plus.tv.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.life.plus.tv.domain.RequestState
import com.life.plus.tv.domain.model.ShowInfo
import com.life.plus.tv.domain.model.UserInfo
import com.life.plus.tv.domain.repo.MainRepo
import com.life.plus.tv.domain.use_cases.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repo: MainRepo,
    private val authUseCases: AuthUseCases
): ViewModel() {
    var keepSplashScreen : Boolean
        get() = savedStateHandle["keepSplashScreen"] ?: true
        set(value) { savedStateHandle["keepSplashScreen"]= value }


    val currentUser = MutableSharedFlow<UserInfo?>()

    val currentUserState = savedStateHandle.getStateFlow<UserInfo?>("currentUser",null)
    val loginInfo = MutableSharedFlow<RequestState<UserInfo>>()
    val regInfo = MutableSharedFlow<RequestState<String>>()

    val searchList = savedStateHandle.getStateFlow<List<ShowInfo>>("searchList", listOf())

    init {
        viewModelScope.launch {
            repo.getLoggedInUser().collect{
                savedStateHandle["currentUser"]= it
                currentUser.emit(it)
            }
        }
    }


    fun login(userName: String, password: String){
        viewModelScope.launch {
            loginInfo.emit(authUseCases.login(userName, password))
        }
    }

    fun logout(){
        viewModelScope.launch {
            currentUserState.value?.userName?.let {
                repo.updateLoginStatus(it, false)
            }
        }
    }

    fun register(userInfo: UserInfo, rePass: String){
        viewModelScope.launch {
            regInfo.emit(authUseCases.register(userInfo, rePass))
        }
    }

    suspend fun isUserExist(userName: String)= repo.getUser(userName) != null

    private var searchJob: Job? = null
    fun searchShow(query: String, error :(String) -> Unit){
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(300)
            val result = repo.getShows(query)
            when (result) {
                is RequestState.Success -> savedStateHandle["searchList"] = result.data
                is RequestState.Error -> error(result.error)
                else -> Unit
            }
        }

    }

}