package com.wsr.fetch

sealed class FetchPasswordPairUseCaseException : Throwable() {
    class NoSuchPasswordGroupException : FetchPasswordPairUseCaseException()
    class SystemError(
        override val message: String,
        override val cause: Throwable,
    ) : FetchPasswordPairUseCaseException()
}
