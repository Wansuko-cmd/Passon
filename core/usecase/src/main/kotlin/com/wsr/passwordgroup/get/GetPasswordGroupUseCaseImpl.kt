package com.wsr.passwordgroup.get

import com.wsr.exceptions.GetDataFailedException
import com.wsr.passwordgroup.PasswordGroupRepository
import com.wsr.passwordgroup.PasswordGroupUseCaseModel
import com.wsr.passwordgroup.toUseCaseModel
import com.wsr.state.State
import com.wsr.ext.UniqueId
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class GetPasswordGroupUseCaseImpl(
    private val passwordGroupRepository: PasswordGroupRepository
) : GetPasswordGroupUseCase {
    private val _data =
        MutableStateFlow<State<PasswordGroupUseCaseModel, GetDataFailedException>>(State.Loading)
    override val data get() = _data.asStateFlow()

    override suspend fun getById(id: String) {
        try {
            val passwordGroup = passwordGroupRepository
                .getById(UniqueId(id))
                .toUseCaseModel()

            _data.emit(State.Success(passwordGroup))
        } catch (e: GetDataFailedException) {
            _data.emit(State.Failure(e))
        }
    }
}