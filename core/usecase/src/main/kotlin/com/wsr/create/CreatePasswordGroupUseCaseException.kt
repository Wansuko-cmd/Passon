package com.wsr.create

sealed class CreatePasswordGroupUseCaseException : Throwable() {
    class SystemError(
        override val message: String,
        override val cause: Throwable,
    ) : CreatePasswordGroupUseCaseException()
}
