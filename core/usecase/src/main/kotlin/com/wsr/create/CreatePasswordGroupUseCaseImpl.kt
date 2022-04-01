package com.wsr.create

import com.wsr.PasswordGroupUseCaseModel
import com.wsr.exceptions.CreateDataFailedException
import com.wsr.maybe.Maybe
import com.wsr.maybe.mapBoth
import com.wsr.passwordgroup.PasswordGroupFactory
import com.wsr.passwordgroup.PasswordGroupRepository
import com.wsr.passwordgroup.Title
import com.wsr.toUseCaseModel
import com.wsr.user.Email

class CreatePasswordGroupUseCaseImpl(
    private val passwordGroupRepository: PasswordGroupRepository,
) : CreatePasswordGroupUseCase {

    private val passwordGroupFactory = PasswordGroupFactory()

    override suspend fun create(
        email: String,
        title: String,
    ): Maybe<PasswordGroupUseCaseModel, CreatePasswordGroupUseCaseException> {
        val passwordGroup = passwordGroupFactory.create(
            email = Email(email),
            title = Title(title),
        )
        return passwordGroupRepository.create(passwordGroup).mapBoth(
            success = { passwordGroup.toUseCaseModel() },
            failure = { it.toCreatePasswordGroupUseCaseException() }
        )
    }

    private fun CreateDataFailedException.toCreatePasswordGroupUseCaseException() = when(this) {
        is CreateDataFailedException.DatabaseError ->
            CreatePasswordGroupUseCaseException.SystemError(
                message = this.message,
                cause = this,
            )
    }
}

sealed class CreatePasswordGroupUseCaseException : Throwable() {
    data class SystemError(
        override val message: String,
        override val cause: Throwable,
    ) : CreatePasswordGroupUseCaseException()
}
