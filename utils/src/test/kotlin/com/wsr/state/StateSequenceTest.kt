@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.state

import com.google.common.truth.Truth.assertThat
import kotlin.test.Test

class StateSequenceTest {
    @Test
    fun listの中身の型が全てSuccessの時はSuccess型を返す() {
        val mockedList = List(5) { State.Success(it) }
        val actual = mockedList.sequence()
        val expected = State.Success(List(5) { it })
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun listの中身にFailureがあるときはFailure型を返す() {
        val mockedList = List(5) { State.Success(it) } + State.Failure("Failure")
        val actual = mockedList.sequence()
        val expected = State.Failure("Failure")
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun listの中身にLoadingがあるときはLoading型を返す() {
        val mockedList = List(5) { State.Success(it) } + State.Loading
        val actual = mockedList.sequence()
        val expected = State.Loading
        assertThat(actual).isEqualTo(expected)
    }
}
