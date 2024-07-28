package com.life.plus.tv.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
sealed class RequestState<out T> : Parcelable {
    data object Idle : RequestState<Nothing>()
    data object Loading : RequestState<Nothing>()
    data class Success<T>(val data: @RawValue T) : RequestState<T>()
    data class Error(val error: String, val code: ErrorType = ErrorType.None) :
        RequestState<Nothing>()
}


enum class ErrorType {
    None,
    InvalidUserName,
    InvalidName,
    InvalidPhone,
    InvalidPass
}
