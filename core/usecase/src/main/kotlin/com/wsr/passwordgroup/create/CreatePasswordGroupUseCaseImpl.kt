package com.wsr.passwordgroup.create

import com.wsr.exceptions.CreateDataFailedException
import com.wsr.passwordgroup.PasswordGroup
import com.wsr.passwordgroup.PasswordGroupRepository
import com.wsr.state.State
import com.wsr.user.Email
import com.wsr.utils.UniqueId

class CreatePasswordGroupUseCaseImpl(
    private val passwordGroupRepository: PasswordGroupRepository
) : CreatePasswordGroupUseCase {

    override suspend fun create(
        email: String,
        title: String
    ): State<Boolean, CreateDataFailedException> = try {

        val passwordGroup = PasswordGroup(
            id = UniqueId(),
            email = Email(email),
            title = title,
            remark = "",
        )

        passwordGroupRepository.create(passwordGroup)

        State.Success(true)

    } catch (e: CreateDataFailedException) {
        State.Failure(e)
    }

}