package com.wsr.exceptions

sealed class DeleteDataFailedException : Throwable() {
    data class NoSuchElementException(override val message: String = "") : DeleteDataFailedException()
    data class SystemError(override val message: String = "") : DeleteDataFailedException()
}
