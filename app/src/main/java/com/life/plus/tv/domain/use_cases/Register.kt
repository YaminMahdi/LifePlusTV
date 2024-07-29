package com.life.plus.tv.domain.use_cases

import com.life.plus.tv.domain.ErrorType
import com.life.plus.tv.domain.RequestState
import com.life.plus.tv.domain.model.UserInfo
import com.life.plus.tv.domain.repo.MainRepo
import com.life.plus.tv.utils.isValidUsername
import javax.inject.Inject

class Register @Inject constructor(
    private val repo: MainRepo
) {
    suspend operator fun invoke(userInfo: UserInfo, rePass: String) =
        if (userInfo.name.isBlank())
            RequestState.Error("Name can't be blank", ErrorType.InvalidName)
        else if (userInfo.userName.isBlank())
            RequestState.Error("User name can't be blank", ErrorType.InvalidUserName)
        else if (!userInfo.userName.isValidUsername())
            RequestState.Error("Please enter a valid user name", ErrorType.InvalidUserName)
        else if (userInfo.phone.length < 10)
            RequestState.Error("Please enter a valid phone Number", ErrorType.InvalidPhone)
        else if (userInfo.password.length < 4)
            RequestState.Error("Password is too short", ErrorType.InvalidPass)
        else if (userInfo.password != rePass)
            RequestState.Error("Password doesn't match", ErrorType.InvalidRePass)
        else
            repo.register(userInfo)
}