package com.wsr.exceptions

sealed class GetAllException : Throwable() {
    class DatabaseException : GetAllException()
}