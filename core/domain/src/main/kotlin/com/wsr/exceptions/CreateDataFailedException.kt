package com.wsr.exceptions

sealed class CreateDataFailedException : Throwable() {
    data class AlreadyExistException(override val message: String = "") : CreateDataFailedException()
    data class SystemError(override val message: String = "") : CreateDataFailedException()
}
