package com.wsr.auth

import com.wsr.maybe.Maybe
import com.wsr.maybe.mapBoth
import com.wsr.queryservice.UserQueryService
import com.wsr.queryservice.UserQueryServiceException
import com.wsr.user.LoginPassword
import com.wsr.user.UserId

class LoginUseCaseImpl(
    private val userQueryService: UserQueryService,
) : LoginUseCase {

    override suspend fun shouldPass(
        userId: String,
        password: String,
    ): Maybe<Unit, LoginUseCaseException> = try {
        val check = userQueryService.get(UserId(userId)).mapBoth(
            success = { user -> user.shouldPass(LoginPassword.PlainLoginPassword(password)) },
            failure = { it.toLoginUseCaseException() }
        )

        when (check) {
            is Maybe.Success ->
                if (check.value) Maybe.Success(Unit)
                else Maybe.Failure(LoginUseCaseException.AuthenticationException(""))
            is Maybe.Failure -> check
        }
    } catch (e: LoginUseCaseException) {
        Maybe.Failure(e)
    }

    private fun UserQueryServiceException.toLoginUseCaseException() = when (this) {
        is UserQueryServiceException.NoSuchUserException ->
            LoginUseCaseException.NoSuchUserException("")
        is UserQueryServiceException.SystemError ->
            throw this
    }
}
