package com.wsr.maybe

sealed class Maybe<out T, out E> {
    data class Success<T>(val value: T) : Maybe<T, Nothing>()
    data class Failure<E>(val value: E) : Maybe<Nothing, E>()
}

inline fun <T, E, NT, NE> Maybe<T, E>.mapBoth(
    success: (T) -> NT,
    failure: (E) -> NE,
): Maybe<NT, NE> =
    when (this) {
        is Maybe.Success -> Maybe.Success(success(value))
        is Maybe.Failure -> Maybe.Failure(failure(value))
    }

inline fun <T, E, NT> Maybe<T, E>.map(block: (T) -> NT): Maybe<NT, E> =
    when (this) {
        is Maybe.Success -> Maybe.Success(block(value))
        is Maybe.Failure -> this
    }

inline fun <T, E> Maybe<T, E>.consume(
    success: (T) -> Unit,
    failure: (E) -> Unit,
) {
    when (this) {
        is Maybe.Success -> success(value)
        is Maybe.Failure -> failure(value)
    }
}

fun <T, E> List<Maybe<T, E>>.sequence(): Maybe<List<T>, E> {
    val result = mutableListOf<T>()
    for (element in this) {
        when (element) {
            is Maybe.Success -> result.add(element.value)
            is Maybe.Failure -> return Maybe.Failure(element.value)
        }
    }
    return Maybe.Success(result)
}
