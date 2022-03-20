package com.wsr.passwordgroup.create

import com.wsr.email.Email
import com.wsr.exceptions.CreateDataFailedException
import com.wsr.passwordgroup.PasswordGroupFactory
import com.wsr.passwordgroup.PasswordGroupRepository
import com.wsr.passwordgroup.PasswordGroupUseCaseModel
import com.wsr.passwordgroup.toUseCaseModel
import com.wsr.state.State

class CreatePasswordGroupUseCaseImpl(
    private val passwordGroupRepository: PasswordGroupRepository,
) : CreatePasswordGroupUseCase {

    private val passwordGroupFactory = PasswordGroupFactory()

    override suspend fun create(
        email: String,
        title: String,
    ): State<PasswordGroupUseCaseModel, CreateDataFailedException> = try {
        val passwordGroup = passwordGroupFactory.create(
            email = Email(email),
            title = title,
        )

        State.Success(passwordGroupRepository.create(passwordGroup).toUseCaseModel())
    } catch (e: CreateDataFailedException) {
        State.Failure(e)
    }
}
