package com.wsr.passwordgroup.create

import com.wsr.exceptions.CreateDataFailedException
import com.wsr.passwordgroup.PasswordGroup
import com.wsr.passwordgroup.PasswordGroupRepository
import com.wsr.passwordgroup.PasswordGroupUseCaseModel
import com.wsr.passwordgroup.toUseCaseModel
import com.wsr.state.State
import com.wsr.user.Email

class CreatePasswordGroupUseCaseImpl(
    private val passwordGroupRepository: PasswordGroupRepository
) : CreatePasswordGroupUseCase {

    override suspend fun create(
        email: String,
        title: String
    ): State<PasswordGroupUseCaseModel, CreateDataFailedException> = try {
        val passwordGroup = PasswordGroup.of(
            email = Email.of(email),
            title = title,
        )

        passwordGroupRepository.create(passwordGroup)

        State.Success(passwordGroup.toUseCaseModel())

    } catch (e: CreateDataFailedException) {
        State.Failure(e)
    }
}