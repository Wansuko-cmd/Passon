package com.wsr.auth

import com.wsr.exceptions.GetDataFailedException
import com.wsr.state.State
import com.wsr.user.Email
import com.wsr.user.LoginPassword

class LoginUseCaseImpl(
    private val loginUseCaseQueryService: LoginUseCaseQueryService,
) : LoginUseCase {

    override suspend fun shouldPass(
        email: String,
        password: String,
    ): State<Boolean, GetDataFailedException> = try {
        val submittedPassword = LoginPassword.PlainLoginPassword(password).toHashed()
        val actualPassword = loginUseCaseQueryService.getPassword(Email(email))
        State.Success(submittedPassword == actualPassword)
    } catch (e: GetDataFailedException) {
        State.Failure(e)
    }
}
