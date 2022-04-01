package com.wsr.fetch

import com.wsr.PasswordPairUseCaseModel
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.maybe.Maybe
import com.wsr.maybe.mapBoth
import kotlinx.coroutines.flow.MutableMaybeFlow

class FetchPasswordPairUseCaseImpl(
    private val queryService: FetchPasswordPairUseCaseQueryService,
) : FetchPasswordPairUseCase {
    private val _data =
        MutableMaybeFlow<Maybe<PasswordPairUseCaseModel, FetchPasswordPairUseCaseException>>(Maybe.Loading)
    override val data get() = _data

    override suspend fun fetch(passwordGroupId: String) {
        _data.emit(Maybe.Loading)
        val passwordSet = queryService
            .getPasswordPair(PasswordGroupId(passwordGroupId))
            .mapBoth(
                success = { it },
                failure = { FetchPasswordPairUseCaseException.SystemError(it.message.orEmpty(), it) }
            )

        _data.emit(passwordSet)
    }
}
