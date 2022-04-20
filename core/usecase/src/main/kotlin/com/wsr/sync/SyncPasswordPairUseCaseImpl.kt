package com.wsr.sync

import com.wsr.PasswordItemUseCaseModel
import com.wsr.exceptions.DeleteDataFailedException
import com.wsr.exceptions.UpdateDataFailedException
import com.wsr.exceptions.UpsertDataFailedException
import com.wsr.maybe.Maybe
import com.wsr.maybe.map
import com.wsr.maybe.mapBoth
import com.wsr.maybe.mapFailure
import com.wsr.maybe.sequence
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passwordgroup.PasswordGroupRepository
import com.wsr.passworditem.Name
import com.wsr.passworditem.Password
import com.wsr.passworditem.PasswordItemFactory
import com.wsr.passworditem.PasswordItemId
import com.wsr.passworditem.PasswordItemRepository
import com.wsr.queryservice.PasswordItemsQueryService
import com.wsr.queryservice.PasswordItemsQueryServiceException

class SyncPasswordPairUseCaseImpl(
    private val passwordGroupRepository: PasswordGroupRepository,
    private val passwordItemRepository: PasswordItemRepository,
    private val passwordItemQueryService: PasswordItemsQueryService,
) : SyncPasswordPairUseCase {

    private val passwordItemFactory = PasswordItemFactory()

    override suspend fun sync(
        passwordGroupId: String,
        title: String,
        remark: String,
        passwordItems: List<PasswordItemUseCaseModel>
    ): Maybe<Unit, SyncPasswordPairUseCaseException> {
        val updatePasswordGroup = passwordGroupRepository.update(PasswordGroupId(passwordGroupId), title, remark)
            .mapFailure { it.toSyncPasswordPairUseCaseException() }

        if (updatePasswordGroup is Maybe.Failure) return updatePasswordGroup

        val deletePasswordItems = deleteAllWithout(
            passwordItems = passwordItems,
            passwordGroupId = PasswordGroupId(passwordGroupId)
        )
        if (deletePasswordItems is Maybe.Failure) return deletePasswordItems

        val updatePasswordItems = passwordItems.map {
            val newPasswordItem = passwordItemFactory.create(
                passwordItemId = PasswordItemId(it.id),
                passwordGroupId = PasswordGroupId(it.passwordGroupId),
                name = Name(it.name),
                password = Password(it.password),
            )
            passwordItemRepository.upsert(newPasswordItem)
        }
            .sequence()
            .mapBoth(
                success = { Unit },
                failure = { it.toSyncPasswordPairUseCaseException() }
            )

        return updatePasswordItems
    }

    private suspend fun deleteAllWithout(
        passwordItems: List<PasswordItemUseCaseModel>,
        passwordGroupId: PasswordGroupId,
    ): Maybe<Unit, SyncPasswordPairUseCaseException> {
        val passwordItemIds = passwordItems.map { passwordItem -> passwordItem.id }
        val shouldDeletePasswordItemIds = passwordItemQueryService
            .getAll(passwordGroupId)
            .mapFailure { it.toSyncPasswordPairUseCaseException() }
            .map { value -> value.filterNot { passwordItemIds.contains(it.id.value) } }

        return when (shouldDeletePasswordItemIds) {
            is Maybe.Success ->
                shouldDeletePasswordItemIds.value
                    .map { passwordItemRepository.delete(it.id) }
                    .sequence()
                    .mapBoth(
                        success = { Unit },
                        failure = { it.toSyncPasswordPairUseCaseException() },
                    )
            is Maybe.Failure -> shouldDeletePasswordItemIds
        }
    }

    private fun UpdateDataFailedException.toSyncPasswordPairUseCaseException() = when (this) {
        is UpdateDataFailedException.NoSuchElementException ->
            SyncPasswordPairUseCaseException.NoSuchPasswordGroupException("")
        is UpdateDataFailedException.SystemError ->
            throw this
    }

    private fun DeleteDataFailedException.toSyncPasswordPairUseCaseException(): SyncPasswordPairUseCaseException = when (this) {
        is DeleteDataFailedException.NoSuchElementException ->
            throw this
        is DeleteDataFailedException.SystemError ->
            throw this
    }

    private fun UpsertDataFailedException.toSyncPasswordPairUseCaseException(): SyncPasswordPairUseCaseException = when (this) {
        is UpsertDataFailedException.SystemError ->
            throw this
    }

    private fun PasswordItemsQueryServiceException.toSyncPasswordPairUseCaseException(): SyncPasswordPairUseCaseException = when (this) {
        is PasswordItemsQueryServiceException.NoSuchUserException ->
            throw this
        is PasswordItemsQueryServiceException.SystemError ->
            throw this
    }
}
