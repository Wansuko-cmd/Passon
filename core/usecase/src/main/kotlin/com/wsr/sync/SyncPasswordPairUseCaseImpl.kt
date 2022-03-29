package com.wsr.sync

import com.wsr.PasswordItemUseCaseModel
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passwordgroup.PasswordGroupRepository
import com.wsr.passworditem.Name
import com.wsr.passworditem.Password
import com.wsr.passworditem.PasswordItemFactory
import com.wsr.passworditem.PasswordItemId
import com.wsr.passworditem.PasswordItemRepository

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
    ) {
        passwordGroupRepository.update(PasswordGroupId(passwordGroupId), title, remark)
        val passwordItemIds = passwordItems.map { passwordItem -> passwordItem.id }
        queryService
            .getAllPasswordItemId(PasswordGroupId(passwordGroupId))
            .filterNot { passwordItemIds.contains(it.value) }
            .forEach { passwordItemRepository.delete(it) }

        passwordItems.forEach {
            val newPasswordItem = passwordItemFactory.create(
                passwordItemId = PasswordItemId(it.id),
                passwordGroupId = PasswordGroupId(it.passwordGroupId),
                name = Name(it.name),
                password = Password(it.password),
            )
            passwordItemRepository.upsert(newPasswordItem)
        }
    }
}
