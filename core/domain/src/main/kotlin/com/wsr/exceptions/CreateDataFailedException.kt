package com.wsr.exceptions

sealed class CreateDataFailedException : Throwable() {
    data class SystemError(override val message: String = "") : CreateDataFailedException()
}
