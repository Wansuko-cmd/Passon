package com.wsr.fetch

import com.wsr.PasswordGroupUseCaseModel
import com.wsr.PasswordItemUseCaseModel
import com.wsr.exceptions.GetAllDataFailedException
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.state.State
import com.wsr.toUseCaseModel
import kotlinx.coroutines.flow.MutableStateFlow

class FetchPasswordSetUseCaseImpl(
    private val queryService: FetchPasswordSetUseCaseQueryService,
) : FetchPasswordSetUseCase {
    private val _data =
        MutableStateFlow<State<Pair<PasswordGroupUseCaseModel, List<PasswordItemUseCaseModel>>, GetAllDataFailedException>>(State.Loading)
    override val data get() = _data

    override suspend fun fetch(passwordGroupId: String) {
        try {
            _data.emit(State.Loading)
            val passwordSet = queryService
                .get(PasswordGroupId(passwordGroupId))
                .let { (first, second) -> first.toUseCaseModel() to second.map { it.toUseCaseModel() } }

            _data.emit(State.Success(passwordSet))
        } catch (e: GetAllDataFailedException) {
            _data.emit(State.Failure(e))
        }
    }
}
