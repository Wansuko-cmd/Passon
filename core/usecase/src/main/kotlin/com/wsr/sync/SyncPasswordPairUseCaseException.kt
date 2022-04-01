package com.wsr.sync

sealed class SyncPasswordPairUseCaseException : Throwable() {
    data class NoSuchPasswordGroupException(override val message: String) : SyncPasswordPairUseCaseException()
    data class SystemError(
        override val message: String,
        override val cause: Throwable,
    ) : SyncPasswordPairUseCaseException()
}
