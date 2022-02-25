package com.wsr.exceptions

sealed class CreateDataFailedException : Throwable() {
    class DatabaseException : CreateDataFailedException()
}