@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.maybe

import com.google.common.truth.Truth.assertThat
import kotlin.test.Test

class MaybeSequenceTest {
    @Test
    fun listの中身の型が全てSuccessの時はSuccess型を返す() {
        val mockedList = List(5) { Maybe.Success(it) }
        val actual = mockedList.sequence()
        val expected = Maybe.Success(List(5) { it })
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun listの中身にFailureがあるときはFailure型を返す() {
        val mockedList = List(5) { Maybe.Success(it) } + Maybe.Failure("Failure")
        val actual = mockedList.sequence()
        val expected = Maybe.Failure("Failure")
        assertThat(actual).isEqualTo(expected)
    }
}
