package com.wsr.utils

sealed class State<T, E> {
    class Loading<T, E> : State<T, E>()
    class Success<T, E>(val value: T) : State<T, E>()
    class Failure<T, E>(val value: E) : State<T, E>()
}

fun <T, E> State<T, E>.execute(
    onLoading: () -> Unit,
    onSuccess: (T) -> Unit,
    onFailure: (E) -> Unit
): State<T, E> {
    when(this) {
        is State.Loading -> onLoading()
        is State.Success -> onSuccess(this.value)
        is State.Failure -> onFailure(this.value)
    }
    return this
}
