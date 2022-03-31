package com.wsr.fetch

import com.wsr.create.CreatePasswordGroupUseCaseException

sealed class FetchPasswordPairUseCaseException : Throwable() {
    class NoSuchPasswordGroupException : FetchPasswordPairUseCaseException()
    class SystemError(
        override val message: String,
        override val cause: Throwable,
    ) : FetchPasswordPairUseCaseException()
}