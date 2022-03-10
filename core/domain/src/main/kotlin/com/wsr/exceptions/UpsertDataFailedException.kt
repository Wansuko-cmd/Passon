package com.wsr.exceptions

sealed class UpsertDataFailedException : Exception() {
    data class DatabaseException(override val message: String = "") : UpsertDataFailedException()
}