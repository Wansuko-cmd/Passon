package com.wsr.exceptions

sealed class UpdateDataFailedException : Throwable() {
    data class DatabaseException(override val message: String = "") : UpdateDataFailedException()
    data class NoSuchElementException(override val message: String = "") :
        UpdateDataFailedException()
}