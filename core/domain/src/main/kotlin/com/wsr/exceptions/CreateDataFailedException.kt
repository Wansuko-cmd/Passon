package com.wsr.exceptions

sealed class CreateDataFailedException : Throwable() {
    data class DatabaseError(override val message: String = "") : CreateDataFailedException()
}
