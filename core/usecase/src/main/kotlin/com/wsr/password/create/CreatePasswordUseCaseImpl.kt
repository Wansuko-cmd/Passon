package com.wsr.password.create

import com.wsr.exceptions.CreateDataFailedException
import com.wsr.password.Password
import com.wsr.password.PasswordRepository
import com.wsr.password.PasswordUseCaseModel
import com.wsr.password.toUseCaseModel
import com.wsr.state.State
import com.wsr.utils.UniqueId

class CreatePasswordUseCaseImpl(
    private val passwordRepository: PasswordRepository,
) : CreatePasswordUseCase {

    override suspend fun create(passwordGroupId: String): State<PasswordUseCaseModel, CreateDataFailedException> = try {
        val newPassword = Password(
            id = UniqueId(),
            passwordGroupId = UniqueId(passwordGroupId),
            name = "",
            password = "",
        )

        passwordRepository.create(newPassword)

        State.Success(newPassword.toUseCaseModel())

    } catch (e: CreateDataFailedException) {
        State.Failure(e)
    }
}