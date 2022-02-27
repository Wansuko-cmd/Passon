package com.wsr.exceptions

sealed class UpsertDataFailedException : Exception() {
    class DatabaseException : UpdateDataFailedException()
}