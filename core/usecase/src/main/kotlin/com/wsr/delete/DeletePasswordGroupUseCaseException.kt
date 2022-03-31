package com.wsr.delete

sealed class DeletePasswordGroupUseCaseException : Throwable() {
    class NoSuchPasswordGroupException(override val message: String) : DeletePasswordGroupUseCaseException()
    class SystemError(
        override val message: String,
        override val cause: Throwable,
    ) : DeletePasswordGroupUseCaseException()
}