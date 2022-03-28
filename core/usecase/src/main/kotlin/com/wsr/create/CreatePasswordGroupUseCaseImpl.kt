package com.wsr.create

import com.wsr.PasswordGroupUseCaseModel
import com.wsr.email.Email
import com.wsr.exceptions.CreateDataFailedException
import com.wsr.passwordgroup.PasswordGroupFactory
import com.wsr.passwordgroup.PasswordGroupRepository
import com.wsr.passwordgroup.Title
import com.wsr.state.State
import com.wsr.toUseCaseModel

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
            title = Title(title),
        )
        passwordGroupRepository.create(passwordGroup)
        State.Success(passwordGroup.toUseCaseModel())
    } catch (e: CreateDataFailedException) {
        State.Failure(e)
    }
}
