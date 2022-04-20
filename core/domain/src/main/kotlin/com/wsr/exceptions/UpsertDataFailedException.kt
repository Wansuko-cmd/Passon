package com.wsr.exceptions

sealed class UpsertDataFailedException : Throwable() {
    data class SystemError(override val message: String = "") : UpsertDataFailedException()
}
