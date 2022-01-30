package com.wsr.utils

sealed class State<out T, out E> {
    object Loading : State<Nothing, Nothing>()
    class Success<T>(val value: T) : State<T, Nothing>()
    class Failure<E>(val value: E) : State<Nothing, E>()
}

fun <T, E> State<T, E>.execute(
    onLoading: () -> Unit,
    onSuccess: (T) -> Unit,
    onFailure: (E) -> Unit
): State<T, E> {
    when (this) {
        is State.Loading -> onLoading()
        is State.Success -> onSuccess(this.value)
        is State.Failure -> onFailure(this.value)
    }
    return this
}
