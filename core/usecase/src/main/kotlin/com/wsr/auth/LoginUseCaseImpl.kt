package com.wsr.auth

import com.wsr.state.State
import com.wsr.state.mapBoth
import com.wsr.user.Email
import com.wsr.user.LoginPassword

class LoginUseCaseImpl(
    private val loginUseCaseQueryService: LoginUseCaseQueryService,
) : LoginUseCase {

    override suspend fun shouldPass(
        email: String,
        password: String,
    ): State<Boolean, LoginUseCaseException> = try {
        val submittedPassword = LoginPassword.PlainLoginPassword(password).toHashed()
        loginUseCaseQueryService.getPassword(Email(email)).mapBoth(
            success = { submittedPassword == it },
            failure = { exception: LoginUseCaseQueryServiceException ->
                when (exception) {
                    is LoginUseCaseQueryServiceException.NoSuchUserException ->
                        LoginUseCaseException.NoSuchUserException("")
                    is LoginUseCaseQueryServiceException.DatabaseError ->
                        LoginUseCaseException.SystemError(
                            message = exception.message,
                            cause = exception,
                        )
                }
            }
        )
    } catch (e: LoginUseCaseException) {
        State.Failure(e)
    }
}
