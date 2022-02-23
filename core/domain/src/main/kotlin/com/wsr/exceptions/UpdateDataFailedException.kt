package com.wsr.exceptions

sealed class UpdateDataFailedException : Throwable() {
    class DatabaseException : UpdateDataFailedException()
    class NoSuchElementException : UpdateDataFailedException()
}