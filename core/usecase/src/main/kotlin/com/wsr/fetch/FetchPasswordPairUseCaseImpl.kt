package com.wsr.fetch

import com.wsr.PasswordPairUseCaseModel
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.state.State
import kotlinx.coroutines.flow.MutableStateFlow

class FetchPasswordPairUseCaseImpl(
    private val queryService: FetchPasswordPairUseCaseQueryService,
) : FetchPasswordPairUseCase {
    private val _data =
        MutableStateFlow<State<PasswordPairUseCaseModel, FetchPasswordPairUseCaseException>>(State.Loading)
    override val data get() = _data

    override suspend fun fetch(passwordGroupId: String) {
        try {
            _data.emit(State.Loading)
            val passwordSet = queryService
                .getPasswordPair(PasswordGroupId(passwordGroupId))

            _data.emit(State.Success(passwordSet))
        } catch (e: Exception) {
            _data.emit(State.Failure(FetchPasswordPairUseCaseException.SystemError(e.message.orEmpty(), e)))
        }
    }
}
