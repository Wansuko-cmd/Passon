package com.wsr.create

import com.wsr.PasswordGroupUseCaseModel
import com.wsr.exceptions.CreateDataFailedException
import com.wsr.maybe.Maybe
import com.wsr.maybe.mapBoth
import com.wsr.passwordgroup.PasswordGroupFactory
import com.wsr.passwordgroup.PasswordGroupRepository
import com.wsr.passwordgroup.Title
import com.wsr.toUseCaseModel
import com.wsr.user.UserId

class CreatePasswordGroupUseCaseImpl(
    private val passwordGroupRepository: PasswordGroupRepository,
) : CreatePasswordGroupUseCase {

    private val passwordGroupFactory = PasswordGroupFactory()

    override suspend fun create(
        userId: String,
        title: String,
    ): Maybe<PasswordGroupUseCaseModel, CreatePasswordGroupUseCaseException> {
        val passwordGroup = passwordGroupFactory.create(
            userId = UserId(userId),
            title = Title(title),
        )
        return passwordGroupRepository.create(passwordGroup).mapBoth(
            success = { passwordGroup.toUseCaseModel() },
            failure = { it.toCreatePasswordGroupUseCaseException() }
        )
    }

    private fun CreateDataFailedException.toCreatePasswordGroupUseCaseException(): CreatePasswordGroupUseCaseException = when (this) {
        is CreateDataFailedException.SystemError -> throw this
    }
}
