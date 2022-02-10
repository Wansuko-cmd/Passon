package com.wsr.password

import com.wsr.exceptions.GetAllException
import com.wsr.state.State
import com.wsr.utils.UniqueId
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class GetAllPasswordUseCaseImpl(
    private val passwordRepository: PasswordRepository
) : GetAllPasswordUseCase {

    private val _data = MutableStateFlow<State<List<PasswordUseCaseModel>, GetAllException>>(State.Loading)
    override val data get() = _data.asStateFlow()

    override suspend fun getAllByPasswordGroupId(passwordGroupId: String) {
        try {
            val passwords = passwordRepository
                .getAllByPasswordGroupId(UniqueId(passwordGroupId))
                .map { it.toUseCaseModel() }

            _data.emit(State.Success(passwords))
        } catch (e: GetAllException) {
            _data.emit(State.Failure(e))
        }
    }
}