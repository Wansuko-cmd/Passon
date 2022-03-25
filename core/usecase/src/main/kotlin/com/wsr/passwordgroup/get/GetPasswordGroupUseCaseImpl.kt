package com.wsr.passwordgroup.get

import com.wsr.exceptions.GetDataFailedException
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passwordgroup.PasswordGroupUseCaseModel
import com.wsr.passwordgroup.toUseCaseModel
import com.wsr.state.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged

class GetPasswordGroupUseCaseImpl(
    private val getPasswordGroupQueryService: GetPasswordGroupUseCaseQueryService,
) : GetPasswordGroupUseCase {

    private val _data =
        MutableStateFlow<State<PasswordGroupUseCaseModel, GetDataFailedException>>(State.Loading)
    override val data get() = _data.asSharedFlow().distinctUntilChanged()

    override suspend fun getById(id: String) {
        try {
            _data.emit(State.Loading)
            val passwordGroup = getPasswordGroupQueryService
                .getById(PasswordGroupId(id))
                .toUseCaseModel()

            _data.emit(State.Success(passwordGroup))
        } catch (e: GetDataFailedException) {
            _data.emit(State.Failure(e))
        }
    }
}
