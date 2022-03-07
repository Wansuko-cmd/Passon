package com.wsr.state

sealed class State<out T, out E> {
    object Loading : State<Nothing, Nothing>()
    class Success<T>(val value: T) : State<T, Nothing>() {
        override fun equals(other: Any?): Boolean {
            return if (other is Success<*>) value == other.value else false
        }

        override fun hashCode(): Int {
            return value?.hashCode() ?: 0
        }
    }

    class Failure<E>(val value: E) : State<Nothing, E>() {
        override fun equals(other: Any?): Boolean {
            return if (other is Failure<*>) value == other.value else false
        }

        override fun hashCode(): Int {
            return value?.hashCode() ?: 0
        }
    }
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