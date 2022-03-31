package com.wsr.auth

import com.wsr.exceptions.GetDataFailedException
import com.wsr.state.State
import com.wsr.user.Email
import com.wsr.user.LoginPassword
import com.wsr.user.UserFactory
import com.wsr.user.UserRepository

class ResetUseCaseImpl(
    private val userRepository: UserRepository,
    private val queryService: ResetUseCaseQueryService,
) : ResetUseCase {

    private val userFactory = UserFactory()

    override suspend fun reset(
        email: String,
        currentPassword: String,
        newPassword: String,
    ): State<Unit, GetDataFailedException> {
        val submittedPassword = LoginPassword.PlainLoginPassword(currentPassword).toHashed()
        val actualPassword = queryService.getPassword(Email(email))
        if (submittedPassword == actualPassword) {
            userFactory.create(
                email = Email(email),
                loginPassword = LoginPassword.PlainLoginPassword(newPassword),
            ).also { userRepository.update(it) }
            return State.Success(Unit)
        }
        return State.Failure(GetDataFailedException.DatabaseException())
    }
}
