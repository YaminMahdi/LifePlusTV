package com.life.plus.tv.domain.use_cases

import com.life.plus.tv.domain.ErrorType
import com.life.plus.tv.domain.RequestState
import com.life.plus.tv.domain.repo.MainRepo
import javax.inject.Inject

class Login @Inject constructor(
    private val repo: MainRepo
) {
    suspend operator fun invoke(userName: String, password: String) =
        if(userName.isBlank())
            RequestState.Error("Please enter valid User Name", ErrorType.InvalidUserName)
        else if(password.length < 4)
            RequestState.Error("Password too short", ErrorType.InvalidPass)
        else
            repo.login(userName, password)
}