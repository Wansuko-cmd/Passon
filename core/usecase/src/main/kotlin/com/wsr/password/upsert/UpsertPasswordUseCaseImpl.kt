package com.wsr.password.upsert

import com.wsr.exceptions.UpsertDataFailedException
import com.wsr.password.PasswordFactory
import com.wsr.password.PasswordId
import com.wsr.password.PasswordRepository
import com.wsr.password.PasswordUseCaseModel
import com.wsr.password.toUseCaseModel
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.state.State

class UpsertPasswordUseCaseImpl(
    private val passwordRepository: PasswordRepository,
) : UpsertPasswordUseCase {

    private val passwordFactory = PasswordFactory()

    override suspend fun upsert(
        id: String,
        passwordGroupId: String,
        name: String,
        password: String,
    ): State<PasswordUseCaseModel, UpsertDataFailedException> = try {
        val newPassword = passwordFactory.create(
            passwordId = PasswordId(id),
            passwordGroupId = PasswordGroupId(passwordGroupId),
            name = name,
            password = password,
        )
        State.Success(passwordRepository.upsert(newPassword).toUseCaseModel())
    } catch (e: UpsertDataFailedException) {
        State.Failure(e)
    }
}
