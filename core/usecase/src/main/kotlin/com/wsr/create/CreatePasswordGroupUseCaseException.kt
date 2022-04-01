package com.wsr.create

sealed class CreatePasswordGroupUseCaseException : Throwable() {
    data class SystemError(
        override val message: String,
        override val cause: Throwable,
    ) : CreatePasswordGroupUseCaseException()
}
