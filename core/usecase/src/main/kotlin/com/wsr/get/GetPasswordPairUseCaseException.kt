package com.wsr.get

sealed class GetPasswordPairUseCaseException : Throwable() {
    data class NoSuchPasswordGroupException(override val message: String) : GetPasswordPairUseCaseException()
    data class SystemError(
        override val message: String,
        override val cause: Throwable,
    ) : GetPasswordPairUseCaseException()
}
