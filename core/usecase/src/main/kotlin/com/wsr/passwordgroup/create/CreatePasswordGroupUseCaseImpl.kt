package com.wsr.passwordgroup.create

import com.wsr.email.Email
import com.wsr.exceptions.CreateDataFailedException
import com.wsr.passwordgroup.PasswordGroup
import com.wsr.passwordgroup.PasswordGroupRepository
import com.wsr.passwordgroup.PasswordGroupUseCaseModel
import com.wsr.passwordgroup.toUseCaseModel
import com.wsr.state.State

class CreatePasswordGroupUseCaseImpl(
    private val passwordGroupRepository: PasswordGroupRepository,
) : CreatePasswordGroupUseCase {

    override suspend fun create(
        email: String,
        title: String
    ): State<PasswordGroupUseCaseModel, CreateDataFailedException> = try {
        val passwordGroup = PasswordGroup.of(
            email = Email.from(email),
            title = title,
        )

        State.Success(passwordGroupRepository.create(passwordGroup).toUseCaseModel())
    } catch (e: CreateDataFailedException) {
        State.Failure(e)
    }
}
