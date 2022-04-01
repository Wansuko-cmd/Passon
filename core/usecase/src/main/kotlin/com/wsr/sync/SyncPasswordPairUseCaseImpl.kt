package com.wsr.sync

import com.wsr.PasswordItemUseCaseModel
import com.wsr.exceptions.UpdateDataFailedException
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passwordgroup.PasswordGroupRepository
import com.wsr.passworditem.Name
import com.wsr.passworditem.Password
import com.wsr.passworditem.PasswordItemFactory
import com.wsr.passworditem.PasswordItemId
import com.wsr.passworditem.PasswordItemRepository
import com.wsr.state.State
import com.wsr.state.consume
import com.wsr.state.map

class SyncPasswordPairUseCaseImpl(
    private val passwordGroupRepository: PasswordGroupRepository,
    private val passwordItemRepository: PasswordItemRepository,
    private val queryService: SyncPasswordPairUseCaseQueryService,
) : SyncPasswordPairUseCase {

    private val passwordItemFactory = PasswordItemFactory()

    override suspend fun sync(
        passwordGroupId: String,
        title: String,
        remark: String,
        passwordItems: List<PasswordItemUseCaseModel>
    ): State<Unit, SyncPasswordPairUseCaseException> {
        val updateResult = passwordGroupRepository.update(PasswordGroupId(passwordGroupId), title, remark)
        if (updateResult is State.Failure) return when (updateResult.value) {
            is UpdateDataFailedException.NoSuchElementException ->
                State.Failure(SyncPasswordPairUseCaseException.NoSuchPasswordGroupException(""))
            is UpdateDataFailedException.DatabaseException ->
                State.Failure(
                    SyncPasswordPairUseCaseException.SystemError(
                        message = updateResult.value.message.orEmpty(),
                        cause = updateResult.value
                    )
                )
        }

        val passwordItemIds = passwordItems.map { passwordItem -> passwordItem.id }
        queryService
            .getAllPasswordItemId(PasswordGroupId(passwordGroupId))
            .consume(
                success = { value ->
                    value.filterNot { passwordItemIds.contains(it.value) }
                        .forEach { passwordItemRepository.delete(it) }
                },
                failure = {},
                loading = {},
            )

        passwordItems.forEach {
            val newPasswordItem = passwordItemFactory.create(
                passwordItemId = PasswordItemId(it.id),
                passwordGroupId = PasswordGroupId(it.passwordGroupId),
                name = Name(it.name),
                password = Password(it.password),
            )
            passwordItemRepository.upsert(newPasswordItem)
        }
        return State.Success(Unit)
    }
}
