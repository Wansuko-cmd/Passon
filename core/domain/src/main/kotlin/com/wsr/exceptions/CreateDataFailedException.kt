package com.wsr.exceptions

sealed class CreateDataFailedException : Throwable() {
    data class DatabaseException(override val message: String = "") : CreateDataFailedException()
}