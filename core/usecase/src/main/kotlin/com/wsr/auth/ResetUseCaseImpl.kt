package com.wsr.auth

import com.wsr.state.State
import com.wsr.user.Email
import com.wsr.user.LoginPassword
import com.wsr.user.UserFactory
import com.wsr.user.UserRepository
import java.lang.Exception

class ResetUseCaseImpl(
    private val userRepository: UserRepository,
    private val queryService: ResetUseCaseQueryService,
) : ResetUseCase {

    private val userFactory = UserFactory()

    override suspend fun reset(
        email: String,
        currentPassword: String,
        newPassword: String,
    ): State<Unit, ResetUseCaseException> {
        val submittedPassword = LoginPassword.PlainLoginPassword(currentPassword).toHashed()

        return when (val actualPassword = queryService.getPassword(Email(email))) {
            is State.Success -> {
                if (submittedPassword == actualPassword.value) {
                    userFactory.create(
                        email = Email(email),
                        loginPassword = LoginPassword.PlainLoginPassword(newPassword),
                    ).also { userRepository.update(it) }
                    return State.Success(Unit)
                }
                return State.Failure(ResetUseCaseException.AuthenticationFailedException(""))
            }
            is State.Failure -> when (actualPassword.value) {
                is ResetUseCaseQueryServiceException.NoSuchUserException ->
                    State.Failure(ResetUseCaseException.NoSuchUserException(""))
                is ResetUseCaseQueryServiceException.DatabaseError ->
                    State.Failure(
                        ResetUseCaseException.SystemError(
                            message = actualPassword.value.message.orEmpty(),
                            cause = actualPassword.value
                        )
                    )
            }
            is State.Loading -> throw Exception()
        }
    }
}
