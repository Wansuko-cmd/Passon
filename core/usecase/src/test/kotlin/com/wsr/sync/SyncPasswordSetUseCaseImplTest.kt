@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.sync

import com.wsr.email.Email
import com.wsr.passwordgroup.PasswordGroup
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passwordgroup.PasswordGroupRepository
import com.wsr.passwordgroup.Remark
import com.wsr.passwordgroup.Title
import com.wsr.passworditem.Name
import com.wsr.passworditem.Password
import com.wsr.passworditem.PasswordItem
import com.wsr.passworditem.PasswordItemId
import com.wsr.passworditem.PasswordItemRepository
import com.wsr.toUseCaseModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SyncPasswordSetUseCaseImplTest {

    @MockK
    private lateinit var passwordGroupRepository: PasswordGroupRepository
    @MockK
    private lateinit var passwordItemRepository: PasswordItemRepository
    @MockK
    private lateinit var queryService: SyncPasswordPairUseCaseQueryService
    private lateinit var target: SyncPasswordPairUseCase

    @BeforeTest
    fun setup() {
        MockKAnnotations.init(this)
        target = SyncPasswordPairUseCaseImpl(passwordGroupRepository, passwordItemRepository, queryService)
    }

    /*** sync関数 ***/
    @Test
    fun test() = runTest {
        val mockedPasswordGroupId = PasswordGroupId("mockedPasswordGroupId")
        val mockedPasswordItems = List(5) { index ->
            PasswordItem(
                id = PasswordItemId(value = "PasswordItemId$index"),
                passwordGroupId = mockedPasswordGroupId,
                name = Name("mockedName$index"),
                password = Password("mockedPassword$index"),
            )
        }

        val updatedMockedPasswordGroup = PasswordGroup(
            id = mockedPasswordGroupId,
            email = Email("updatedMockedEmail"),
            title = Title("updatedMockedTitle"),
            remark = Remark("updatedMockedRemark"),
        )

        val updatedMockedPasswordItems = List(3) { index ->
            PasswordItem(
                id = PasswordItemId(value = "PasswordItemId$index"),
                passwordGroupId = mockedPasswordGroupId,
                name = Name("updatedMockedName$index"),
                password = Password("updatedMockedPassword$index"),
            )
        }

        coEvery {
            passwordGroupRepository.update(
                id = mockedPasswordGroupId,
                title = updatedMockedPasswordGroup.title.value,
                remark = updatedMockedPasswordGroup.remark.value,
            )
        } returns Unit
        coEvery { passwordItemRepository.delete(PasswordItemId(any())) } returns Unit
        coEvery { passwordItemRepository.upsert(any()) } returns Unit
        coEvery { queryService.getAllPasswordItemId(mockedPasswordGroupId) } returns mockedPasswordItems.map { it.id }

        target.sync(
            passwordGroupId = mockedPasswordGroupId.value,
            title = updatedMockedPasswordGroup.title.value,
            remark = updatedMockedPasswordGroup.remark.value,
            passwordItems = updatedMockedPasswordItems.map { it.toUseCaseModel() }
        )

        coVerify(exactly = 1) {
            passwordGroupRepository.update(
                id = mockedPasswordGroupId,
                title = updatedMockedPasswordGroup.title.value,
                remark = updatedMockedPasswordGroup.remark.value,
            )
            queryService.getAllPasswordItemId(mockedPasswordGroupId)
        }
        coVerify(exactly = 2) { passwordItemRepository.delete(PasswordItemId(any())) }
        coVerify(exactly = 3) { passwordItemRepository.upsert(any()) }
        confirmVerified(passwordGroupRepository, passwordItemRepository, queryService)
    }
}
