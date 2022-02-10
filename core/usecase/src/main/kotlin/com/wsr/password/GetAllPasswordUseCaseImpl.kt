package com.wsr.password

import com.wsr.exceptions.GetAllDataFailedException
import com.wsr.state.State
import com.wsr.utils.UniqueId
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class GetAllPasswordUseCaseImpl(
    private val passwordRepository: PasswordRepository
) : GetAllPasswordUseCase {

    private val _data =
        MutableStateFlow<State<List<PasswordUseCaseModel>, GetAllDataFailedException>>(State.Loading)
    override val data get() = _data.asStateFlow()

    override suspend fun getAllByPasswordGroupId(passwordGroupId: String) {
        try {
            val passwords = passwordRepository
                .getAllByPasswordGroupId(UniqueId(passwordGroupId))
                .map { it.toUseCaseModel() }

            _data.emit(State.Success(passwords))
        } catch (e: GetAllDataFailedException) {
            _data.emit(State.Failure(e))
        }
    }
}