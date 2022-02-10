package com.wsr.exceptions

sealed class GetAllDataFailedException : Throwable() {
    class DatabaseException : GetAllDataFailedException()
}