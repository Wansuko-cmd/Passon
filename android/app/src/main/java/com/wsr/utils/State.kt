package com.wsr.utils

import com.wsr.maybe.Maybe
import com.wsr.maybe.mapBoth

sealed class State<out T, out E> {
    object Loading : State<Nothing, Nothing>()
    data class Success<T>(val value: T) : State<T, Nothing>()
    data class Failure<E>(val value: E) : State<Nothing, E>()
}

inline fun <T, E, NT, NE> State<T, E>.mapBoth(
    success: (T) -> NT,
    failure: (E) -> NE,
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

fun <T, E> List<State<T, E>>.sequence(): State<List<T>, E> {
    val result = mutableListOf<T>()
    for (element in this) {
        when (element) {
            is State.Success -> result.add(element.value)
            is State.Failure -> return State.Failure(element.value)
            is State.Loading -> return State.Loading
        }
    }
    return State.Success(result)
}

fun <T, E> Maybe<T, E>.asState(): State<T, E> = when(this) {
    is Maybe.Success -> State.Success(value)
    is Maybe.Failure -> State.Failure(value)
}
