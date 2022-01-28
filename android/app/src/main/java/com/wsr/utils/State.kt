package com.wsr.utils

sealed class State<T, E> {
    class Loading<T, E> : State<T, E>()
    class Success<T, E>(val value: T) : State<T, E>()
    class Failure<T, E>(val value: E) : State<T, E>()
}

fun <T, E> State<T, E>.onLoading(block: () -> Unit): State<T, E> {
    if (this is State.Loading) block()
    return this
}

fun <T, E> State<T, E>.onSuccess(block: (T) -> Unit): State<T, E> {
    if (this is State.Success) block(value)
    return this
}

fun <T, E> State<T, E>.onFailure(block: (E) -> Unit): State<T, E> {
    if (this is State.Failure) block(value)
    return this
}