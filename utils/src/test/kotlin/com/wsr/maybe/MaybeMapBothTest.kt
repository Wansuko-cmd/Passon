@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.maybe

import com.google.common.truth.Truth.assertThat
import kotlin.test.Test
import kotlin.test.fail

class MaybeMapBothTest {

    /*** mapBoth関数 ***/
    @Test
    fun 型がSuccessの時はsuccessを実行して結果を返す() {
        val mockedMaybe = Maybe.Success("mockedSuccess")
        val updatedMockedMaybe = mockedMaybe.mapBoth(
            success = { "updatedSuccess" },
            failure = { fail("failureの方を実行") },
        )

        val expected = Maybe.Success("updatedSuccess")

        assertThat(updatedMockedMaybe).isEqualTo(expected)
    }

    @Test
    fun 型がFailureの時はfailureを実行して結果を返す() {
        val mockedMaybe = Maybe.Failure("mockedFailure")
        val updatedMockedMaybe = mockedMaybe.mapBoth(
            success = { fail("successの方を実行") },
            failure = { "updatedFailure" },
        )

        val expected = Maybe.Failure("updatedFailure")

        assertThat(updatedMockedMaybe).isEqualTo(expected)
    }
}
