package com.wsr.state

sealed class State<out T, out E> {
    object Loading : State<Nothing, Nothing>()
    class Success<T>(val value: T) : State<T, Nothing>()
    class Failure<E>(val value: E) : State<Nothing, E>()
}

inline fun <T, E, NT, NE> State<T, E>.mapBoth(
    success: (T) -> NT,
    failure: (E) -> NE
): State<NT, NE> =
    when (this) {
        is State.Success -> State.Success(success(value))
        is State.Failure -> State.Failure(failure(value))
        is State.Loading -> this
    }

inline fun <T, E, NT> State<T, E>.map(block: (T) -> NT): State<NT, E> =
    when (this) {
        is State.Success -> State.Success(block(value))
        is State.Loading -> this
        is State.Failure -> this
    }

inline fun <T, E, NT> State<T, E>.flatMap(block: (T) -> State<NT, E>): State<NT, E> =
    when (this) {
        is State.Success -> block(value)
        is State.Loading -> this
        is State.Failure -> this
    }

inline fun <T, E> State<T, E>.consume(
    success: (T) -> Unit,
    failure: (E) -> Unit,
    loading: () -> Unit,
) {
    when (this) {
        is State.Success -> success(value)
        is State.Failure -> failure(value)
        is State.Loading -> loading()
    }
}

fun <T, E> List<State<T, E>>.sequence(): State<List<T>, E> =
    fold(State.Success(listOf())) { acc, state ->
        acc.flatMap { list -> state.map { value -> list + value } }
    }