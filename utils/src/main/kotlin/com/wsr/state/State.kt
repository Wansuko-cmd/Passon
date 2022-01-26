package com.wsr.state

sealed class State<out T> {
    class Success<T>(val value: T): State<T>()
    class Failure(val error: Throwable): State<Nothing>()
}

inline fun <T> runCatching(block: () -> T): State<T> = try {
    State.Success(block())
} catch (e: Throwable) { State.Failure(e) }

fun <T, U> State<T>.flatMap(block: (T) -> State<U>): State<U> = when(this) {
    is State.Success<T> -> try { block(value) } catch (e: Exception) { State.Failure(e) }
    is State.Failure -> this
}

fun <T, U> State<T>.map(block: (T) -> U): State<U> = when(this) {
    is State.Success<T> -> com.wsr.state.runCatching { block(this.value) }
    is State.Failure -> this
}

fun <T> State<T>.mapBoth(onSuccess: (T) -> Unit, onError: (Throwable) -> Unit) = when(this) {
    is State.Success<T> -> onSuccess(this.value)
    is State.Failure -> onError(this.error)
}