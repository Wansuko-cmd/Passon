package com.wsr.exceptions

sealed class GetAllDataFailedException : Throwable() {
    data class DatabaseException(override val message: String = "") : GetAllDataFailedException()
}
