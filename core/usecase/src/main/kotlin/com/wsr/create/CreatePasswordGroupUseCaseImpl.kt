package com.wsr.create

import com.wsr.PasswordGroupUseCaseModel
import com.wsr.exceptions.CreateDataFailedException
import com.wsr.passwordgroup.PasswordGroupFactory
import com.wsr.passwordgroup.PasswordGroupRepository
import com.wsr.passwordgroup.Title
import com.wsr.state.State
import com.wsr.state.mapBoth
import com.wsr.toUseCaseModel
import com.wsr.user.Email

class CreatePasswordGroupUseCaseImpl(
    private val passwordGroupRepository: PasswordGroupRepository,
) : CreatePasswordGroupUseCase {

    private val passwordGroupFactory = PasswordGroupFactory()

    override suspend fun create(
        email: String,
        title: String,
    ): State<PasswordGroupUseCaseModel, CreatePasswordGroupUseCaseException> {
        val passwordGroup = passwordGroupFactory.create(
            email = Email(email),
            title = Title(title),
        )
        passwordGroupRepository.create(passwordGroup).mapBoth(
            success = { passwordGroup.toUseCaseModel() },
            failure = { exception ->
                when (exception) {
                    is CreateDataFailedException.DatabaseException ->
                        CreatePasswordGroupUseCaseException.SystemError(
                            message = exception.message,
                            cause = exception,
                        )
                }
            }
        )
    }
}
