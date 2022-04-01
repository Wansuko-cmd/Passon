package com.wsr.auth

import com.wsr.maybe.Maybe
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
    ): Maybe<Unit, ResetUseCaseException> {
        val submittedPassword = LoginPassword.PlainLoginPassword(currentPassword).toHashed()

        return when (val actualPassword = queryService.getPassword(Email(email))) {
            is Maybe.Success -> {
                if (submittedPassword == actualPassword.value) {
                    userFactory.create(
                        email = Email(email),
                        loginPassword = LoginPassword.PlainLoginPassword(newPassword),
                    ).also { userRepository.update(it) }
                    return Maybe.Success(Unit)
                }
                return Maybe.Failure(ResetUseCaseException.AuthenticationFailedException(""))
            }
            is Maybe.Failure -> when (actualPassword.value) {
                is ResetUseCaseQueryServiceException.NoSuchUserException ->
                    Maybe.Failure(ResetUseCaseException.NoSuchUserException(""))
                is ResetUseCaseQueryServiceException.DatabaseError ->
                    Maybe.Failure(
                        ResetUseCaseException.SystemError(
                            message = actualPassword.value.message.orEmpty(),
                            cause = actualPassword.value
                        )
                    )
            }
        }
    }
}
