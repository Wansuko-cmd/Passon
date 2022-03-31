package com.wsr.delete

sealed class DeletePasswordGroupUseCaseException : Throwable() {
    data class NoSuchPasswordGroupException(override val message: String) : DeletePasswordGroupUseCaseException()
    data class SystemError(
        override val message: String,
        override val cause: Throwable,
    ) : DeletePasswordGroupUseCaseException()
}
