package com.wsr.fetch

import com.wsr.PasswordGroupUseCaseModel
import com.wsr.exceptions.GetAllDataFailedException
import com.wsr.state.State
import com.wsr.user.Email
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged

class FetchAllPasswordGroupUseCaseImpl(
    private val queryService: FetchAllPasswordGroupUseCaseQueryService,
) : FetchAllPasswordGroupUseCase {
    private val _data =
        MutableStateFlow<State<List<PasswordGroupUseCaseModel>, GetAllDataFailedException>>(State.Loading)
    override val data get() = _data.asSharedFlow().distinctUntilChanged()

    override suspend fun fetch(email: String) {
        try {
            _data.emit(State.Loading)
            val passwordGroups = queryService
                .getAllPasswordGroup(Email(email))

            _data.emit(State.Success(passwordGroups))
        } catch (e: GetAllDataFailedException) {
            _data.emit(State.Failure(e))
        }
    }
}
