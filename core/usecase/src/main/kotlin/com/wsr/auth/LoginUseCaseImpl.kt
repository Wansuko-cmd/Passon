package com.wsr.auth

import com.wsr.maybe.Maybe
import com.wsr.maybe.mapBoth
import com.wsr.queryservice.UserQueryService
import com.wsr.queryservice.UserQueryServiceException
import com.wsr.user.UserId
import com.wsr.user.LoginPassword

class LoginUseCaseImpl(
    private val userQueryService: UserQueryService,
) : LoginUseCase {

    override suspend fun shouldPass(
        email: String,
        password: String,
    ): Maybe<Boolean, LoginUseCaseException> = try {
        userQueryService.get(UserId(email)).mapBoth(
            success = { user -> user.shouldPass(LoginPassword.PlainLoginPassword(password)) },
            failure = { it.toLoginUseCaseException() }
        )
    } catch (e: LoginUseCaseException) {
        Maybe.Failure(e)
    }

    private fun UserQueryServiceException.toLoginUseCaseException() = when (this) {
        is UserQueryServiceException.NoSuchUserException ->
            LoginUseCaseException.NoSuchUserException("")
        is UserQueryServiceException.DatabaseError ->
            LoginUseCaseException.SystemError(
                message = this.message,
                cause = this,
            )
    }
}
