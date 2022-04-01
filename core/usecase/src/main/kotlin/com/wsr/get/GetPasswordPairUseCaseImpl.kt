package com.wsr.get

import com.wsr.maybe.mapFailure
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.queryservice.PasswordPairQueryService
import com.wsr.queryservice.PasswordPairQueryServiceException

class GetPasswordPairUseCaseImpl(
    private val passwordPairQueryService: PasswordPairQueryService,
) : GetPasswordPairUseCase {

    override suspend fun get(passwordGroupId: String) =
        passwordPairQueryService
            .get(PasswordGroupId(passwordGroupId))
            .mapFailure { it.toGetPasswordPairUseCaseException() }

    private fun PasswordPairQueryServiceException.toGetPasswordPairUseCaseException() = when(this) {
        is PasswordPairQueryServiceException.NoSuchPasswordGroupException ->
            GetPasswordPairUseCaseException.NoSuchPasswordGroupException("")
        is PasswordPairQueryServiceException.DatabaseError ->
            GetPasswordPairUseCaseException.SystemError(
                message = this.message,
                cause = this,
            )
    }
}
