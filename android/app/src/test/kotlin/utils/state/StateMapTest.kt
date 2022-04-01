@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package utils.state

import com.google.common.truth.Truth.assertThat
import com.wsr.utils.State
import com.wsr.utils.map
import kotlin.test.Test
import kotlin.test.fail

class StateMapTest {

    /*** mapBoth関数 ***/
    @Test
    fun 型がSuccessの時はラムダ式を実行して結果を返す() {
        val mockedState = State.Success("mockedSuccess")
        val updatedMockedState = mockedState.map { "updatedSuccess" }

        val expected = State.Success("updatedSuccess")

        assertThat(updatedMockedState).isEqualTo(expected)
    }

    @Test
    fun 型がFailureの時はそのままの結果を返す() {
        val mockedState = State.Failure("mockedFailure")
        val updatedMockedState = mockedState.map { fail("mapの中身を実行") }

        val expected = State.Failure("mockedFailure")

        assertThat(updatedMockedState).isEqualTo(expected)
    }

    @Test
    fun 型がLoadingの時はLoadingを返す() {
        val mockedState = State.Loading
        val updatedMockedState = mockedState.map { fail("mapの中身を実行") }

        val expected = State.Loading

        assertThat(updatedMockedState).isEqualTo(expected)
    }
}
