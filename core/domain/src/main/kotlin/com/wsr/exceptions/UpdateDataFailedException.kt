package com.wsr.exceptions

sealed class UpdateDataFailedException : Throwable() {
    data class NoSuchElementException(override val message: String = "") :
        UpdateDataFailedException()
    data class DatabaseError(override val message: String = "") : UpdateDataFailedException()
}
