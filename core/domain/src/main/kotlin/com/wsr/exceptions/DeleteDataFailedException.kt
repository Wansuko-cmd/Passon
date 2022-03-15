package com.wsr.exceptions

sealed class DeleteDataFailedException : Throwable() {
    data class DatabaseException(override val message: String = "") : DeleteDataFailedException()
}
