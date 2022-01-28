package com.wsr.utils

sealed class State<T, E> {
    class Loading<T, E> : State<T, E>()
    class Success<T, E>(val value: T) : State<T, E>()
    class Failure<T, E>(val value: E) : State<T, E>()
}

fun <T, E> State<T, E>.onLoading(block: () -> Unit): State<T, E> = when (this) {
    is State.Loading -> {
        block()
        this
    }
    else -> this
}

fun <T, E> State<T, E>.onSuccess(block: (T) -> Unit): State<T, E> = when (this) {
    is State.Success -> {
        block(this.value)
        this
    }
    else -> this
}

fun <T, E> State<T, E>.onFailure(block: (E) -> Unit): State<T, E> = when (this) {
    is State.Failure -> {
        block(this.value)
        this
    }
    else -> this
}