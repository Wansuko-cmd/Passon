package com.wsr.ext

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*

inline fun <T, U> MutableStateFlow<T>.updateWith(
    target: Flow<U>,
    coroutineScope: CoroutineScope,
    crossinline block: (T, U) -> T
) {
    target.onEach { u ->
        update { t ->
            block(t, u)
        }
    }.launchIn(coroutineScope)
}