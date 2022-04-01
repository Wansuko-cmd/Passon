package com.wsr.fetch

import com.wsr.PasswordPairUseCaseModel
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.state.State
import com.wsr.state.mapBoth
import kotlinx.coroutines.flow.MutableStateFlow

class FetchPasswordPairUseCaseImpl(
    private val queryService: FetchPasswordPairUseCaseQueryService,
) : FetchPasswordPairUseCase {
    private val _data =
        MutableStateFlow<State<PasswordPairUseCaseModel, FetchPasswordPairUseCaseException>>(State.Loading)
    override val data get() = _data

    override suspend fun fetch(passwordGroupId: String) {
        _data.emit(State.Loading)
        val passwordSet = queryService
            .getPasswordPair(PasswordGroupId(passwordGroupId))
            .mapBoth(
                success = { it },
                failure = { FetchPasswordPairUseCaseException.SystemError(it.message.orEmpty(), it) }
            )

        _data.emit(passwordSet)
    }
}
