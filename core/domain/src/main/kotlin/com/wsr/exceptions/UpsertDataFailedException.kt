package com.wsr.exceptions

sealed class UpsertDataFailedException : Throwable() {
    data class DatabaseException(override val message: String = "") : UpsertDataFailedException()
}
