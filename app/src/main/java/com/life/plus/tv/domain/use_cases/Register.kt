package com.life.plus.tv.domain.use_cases

import com.life.plus.tv.domain.ErrorType
import com.life.plus.tv.domain.RequestState
import com.life.plus.tv.domain.model.UserInfo
import com.life.plus.tv.domain.repo.MainRepo
import javax.inject.Inject

class Register @Inject constructor(
    private val repo: MainRepo
) {
    suspend operator fun invoke(userInfo: UserInfo) =
        if (userInfo.userName.isBlank())
            RequestState.Error("Please enter valid Name", ErrorType.InvalidName)
        else if (userInfo.userName.isBlank())
            RequestState.Error("Please enter valid User Name", ErrorType.InvalidUserName)
        else if (userInfo.phone.length < 10)
            RequestState.Error("Please enter valid Phone Number", ErrorType.InvalidPhone)
        else if (userInfo.password.length < 4)
            RequestState.Error("Password too short", ErrorType.InvalidPass)
        else
            repo.register(userInfo)
}