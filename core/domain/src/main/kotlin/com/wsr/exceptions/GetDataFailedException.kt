package com.wsr.exceptions

sealed class GetDataFailedException : Throwable() {
    data class DatabaseException(override val message: String = "") : GetDataFailedException()
    data class NoSuchElementException(override val message: String = "") : GetDataFailedException()
}
