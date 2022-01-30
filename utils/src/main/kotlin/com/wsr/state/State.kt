package com.wsr.state

sealed class State<out T, out E> {
    object Loading : State<Nothing, Nothing>()
    class Success<T>(val value: T) : State<T, Nothing>()
    class Failure<E>(val value: E) : State<Nothing, E>()
}

fun <T, E, NT, NE> State<T, E>.mapBoth(success: (T) -> NT, failure: (E) -> NE): State<NT, NE> =
    when (this) {
        is State.Loading -> this
        is State.Success -> State.Success(success(value))
        is State.Failure -> State.Failure(failure(value))
    }
