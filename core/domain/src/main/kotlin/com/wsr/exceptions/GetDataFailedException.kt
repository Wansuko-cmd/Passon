package com.wsr.exceptions

sealed class GetDataFailedException : Throwable() {
    class DatabaseException : GetDataFailedException()
    class NoSuchElementException : GetDataFailedException()
}