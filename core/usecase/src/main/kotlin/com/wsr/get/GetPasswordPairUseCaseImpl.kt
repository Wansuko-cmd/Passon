package com.wsr.get

import com.wsr.maybe.mapBoth
import com.wsr.passwordgroup.PasswordGroupId

class GetPasswordPairUseCaseImpl(
    private val queryService: FetchPasswordPairUseCaseQueryService,
) : GetPasswordPairUseCase {

    override suspend fun get(passwordGroupId: String) =
        queryService
            .getPasswordPair(PasswordGroupId(passwordGroupId))
            .mapBoth(
                success = { it },
                failure = {
                    GetPasswordPairUseCaseException.SystemError(it.message.orEmpty(), it)
                }
            )
}
