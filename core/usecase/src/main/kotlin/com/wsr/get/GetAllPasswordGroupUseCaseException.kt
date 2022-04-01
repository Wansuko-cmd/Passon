package com.wsr.get

sealed class GetAllPasswordGroupUseCaseException : Throwable() {
    data class SystemError(
        override val message: String,
        override val cause: Throwable,
    ) : GetAllPasswordGroupUseCaseException()
}
