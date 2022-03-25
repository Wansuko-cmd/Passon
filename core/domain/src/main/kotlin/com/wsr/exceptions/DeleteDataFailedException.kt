package com.wsr.exceptions

sealed class DeleteDataFailedException : Throwable() {
    data class NoSuchElementException(override val message: String = "") : DeleteDataFailedException()
    data class DatabaseException(override val message: String = "") : DeleteDataFailedException()
}
