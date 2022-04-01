package com.wsr.auth

import com.wsr.state.State
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
        val actualPassword = loginUseCaseQueryService.getPassword(Email(email))
        State.Success(submittedPassword == actualPassword)
    } catch (e: LoginUseCaseException) {
        State.Failure(e)
    }
}
