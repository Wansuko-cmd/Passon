package com.wsr.sync

sealed class SyncPasswordPairUseCaseException : Throwable() {
    class NoSuchPasswordGroupException(override val message: String) : SyncPasswordPairUseCaseException()
    class SystemError(
        override val message: String,
        override val cause: Throwable,
    ) : SyncPasswordPairUseCaseException()
}
