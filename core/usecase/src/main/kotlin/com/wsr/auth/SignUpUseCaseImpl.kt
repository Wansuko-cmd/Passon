package com.wsr.auth

import com.wsr.exceptions.CreateDataFailedException
import com.wsr.maybe.Maybe
import com.wsr.maybe.mapBoth
import com.wsr.user.DatabasePath
import com.wsr.user.DisplayName
import com.wsr.user.LoginPassword
import com.wsr.user.UserFactory
import com.wsr.user.UserRepository

class SignUpUseCaseImpl(private val userRepository: UserRepository) : SignUpUseCase {

    private val userFactory = UserFactory()

    override suspend fun signUp(
        displayName: String,
        databasePath: String,
        loginPassword: String
    ): Maybe<Unit, SignUpUseCaseException> {
        val user = userFactory.create(
            displayName = DisplayName(displayName),
            databasePath = DatabasePath(databasePath),
            loginPassword = LoginPassword.PlainLoginPassword(loginPassword)
        )
        return userRepository.create(user).mapBoth(
            success = { },
            failure = { it.toSignUpUseCaseException() },
        )
    }

    private fun CreateDataFailedException.toSignUpUseCaseException(): SignUpUseCaseException = when (this) {
        is CreateDataFailedException.SystemError -> throw this
    }
}
