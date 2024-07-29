package com.life.plus.tv.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.life.plus.tv.domain.RequestState
import com.life.plus.tv.domain.model.UserInfo
import com.life.plus.tv.domain.repo.MainRepo
import com.life.plus.tv.domain.use_cases.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repo: MainRepo,
    private val authUseCases: AuthUseCases
): ViewModel() {

    val currentUser = savedStateHandle.getStateFlow<UserInfo?>("currentUser",null)
    val loginInfo = MutableSharedFlow<RequestState<UserInfo>>()
    val regInfo = MutableSharedFlow<RequestState<String>>()

    init {
        viewModelScope.launch {
            repo.getLoggedInUser().collect{
                savedStateHandle["currentUser"]= it
            }
        }
    }


    fun login(userName: String, password: String){
        viewModelScope.launch {
            loginInfo.emit(authUseCases.login(userName, password))
        }
    }

    fun register(userInfo: UserInfo, rePass: String){
        viewModelScope.launch {
            regInfo.emit(authUseCases.register(userInfo, rePass))
        }
    }

    suspend fun isUserNameExist(userName: String)= repo.getUser(userName) != null

}