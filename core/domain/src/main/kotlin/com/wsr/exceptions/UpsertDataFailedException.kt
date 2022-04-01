package com.wsr.exceptions

sealed class UpsertDataFailedException : Throwable() {
    data class DatabaseError(override val message: String = "") : UpsertDataFailedException()
}
