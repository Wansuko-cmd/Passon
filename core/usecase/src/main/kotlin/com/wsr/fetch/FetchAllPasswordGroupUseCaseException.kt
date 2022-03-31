package com.wsr.fetch

sealed class FetchAllPasswordGroupUseCaseException : Throwable() {
    class SystemError(
        override val message: String,
        override val cause: Throwable,
    ) : FetchAllPasswordGroupUseCaseException()
}
