package com.life.plus.tv.domain.use_cases

import javax.inject.Inject

data class AuthUseCases @Inject constructor(
    val login: Login,
    val register: Register
)