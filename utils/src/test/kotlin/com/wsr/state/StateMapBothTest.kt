@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.state

import com.google.common.truth.Truth.assertThat
import kotlin.test.Test
import kotlin.test.fail

class StateMapBothTest {

    /*** mapBoth関数 ***/
    @Test
    fun 型がSuccessの時はsuccessを実行して結果を返す() {
        val mockedState = State.Success("mockedSuccess")
        val updatedMockedState = mockedState.mapBoth(
            success = { "updatedSuccess" },
            failure = { fail("failureの方を実行") },
        )

        val expected = State.Success("updatedSuccess")

        assertThat(updatedMockedState).isEqualTo(expected)
    }

    @Test
    fun 型がFailureの時はfailureを実行して結果を返す() {
        val mockedState = State.Failure("mockedFailure")
        val updatedMockedState = mockedState.mapBoth(
            success = { fail("successの方を実行") },
            failure = { "updatedFailure" },
        )

        val expected = State.Failure("updatedFailure")

        assertThat(updatedMockedState).isEqualTo(expected)
    }

    @Test
    fun 型がLoadingの時はLoadingを返す() {
        val mockedState = State.Loading
        val updatedMockedState = mockedState.mapBoth(
            success = { fail("successの方を実行") },
            failure = { fail("failureの方を実行") },
        )

        val expected = State.Loading

        assertThat(updatedMockedState).isEqualTo(expected)
    }
}
