package com.wsr.passwordgroup.get

import com.wsr.exceptions.GetDataFailedException
import com.wsr.passwordgroup.PasswordGroupRepository
import com.wsr.passwordgroup.PasswordGroupUseCaseModel
import com.wsr.passwordgroup.toUseCaseModel
import com.wsr.state.State
import com.wsr.utils.UniqueId
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged

class GetPasswordGroupUseCaseImpl(
    private val passwordGroupRepository: PasswordGroupRepository
) : GetPasswordGroupUseCase {
    private val _data =
        MutableSharedFlow<State<PasswordGroupUseCaseModel, GetDataFailedException>>(replay = 0)
    override val data get() = _data.asSharedFlow().distinctUntilChanged()

    override suspend fun getById(id: String) {
        try {
            _data.emit(State.Loading)
            val passwordGroup = passwordGroupRepository
                .getById(UniqueId.of(id))
                .toUseCaseModel()

            _data.emit(State.Success(passwordGroup))
        } catch (e: GetDataFailedException) {
            _data.emit(State.Failure(e))
        }
    }
}
