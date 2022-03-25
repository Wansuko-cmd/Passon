package com.wsr.passworditem.upsert

import com.wsr.exceptions.UpsertDataFailedException
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passworditem.Name
import com.wsr.passworditem.Password
import com.wsr.passworditem.PasswordItemFactory
import com.wsr.passworditem.PasswordItemId
import com.wsr.passworditem.PasswordItemRepository
import com.wsr.passworditem.PasswordItemUseCaseModel
import com.wsr.passworditem.toUseCaseModel
import com.wsr.state.State

class UpsertPasswordItemUseCaseImpl(
    private val passwordRepository: PasswordItemRepository,
) : UpsertPasswordItemUseCase {

    private val passwordFactory = PasswordItemFactory()

    override suspend fun upsert(
        id: String,
        passwordGroupId: String,
        name: String,
        password: String,
    ): State<PasswordItemUseCaseModel, UpsertDataFailedException> = try {
        val newPassword = passwordFactory.create(
            passwordItemId = PasswordItemId(id),
            passwordGroupId = PasswordGroupId(passwordGroupId),
            name = Name(name),
            password = Password(password),
        )
        passwordRepository.upsert(newPassword)
        State.Success(newPassword.toUseCaseModel())
    } catch (e: UpsertDataFailedException) {
        State.Failure(e)
    }
}
