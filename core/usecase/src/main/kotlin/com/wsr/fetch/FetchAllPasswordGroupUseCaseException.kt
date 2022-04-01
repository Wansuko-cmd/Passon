package com.wsr.fetch

sealed class FetchAllPasswordGroupUseCaseException : Throwable() {
    data class SystemError(
        override val message: String,
        override val cause: Throwable,
    ) : FetchAllPasswordGroupUseCaseException()
}
