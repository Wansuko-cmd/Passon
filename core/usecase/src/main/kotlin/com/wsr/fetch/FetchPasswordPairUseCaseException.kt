package com.wsr.fetch

sealed class FetchPasswordPairUseCaseException : Throwable() {
    data class NoSuchPasswordGroupException(override val message: String) : FetchPasswordPairUseCaseException()
    data class SystemError(
        override val message: String,
        override val cause: Throwable,
    ) : FetchPasswordPairUseCaseException()
}
