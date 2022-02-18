package com.wsr.password.updateall

import com.wsr.password.PasswordRepository
import com.wsr.password.PasswordUseCaseModel
import com.wsr.password.toPassword
import com.wsr.state.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class UpdateAllPasswordUseCaseImpl(
    private val passwordRepository: PasswordRepository
) : UpdateAllPasswordUseCase {

    private val _data = MutableStateFlow<State<Boolean, Throwable>>(State.Loading)
    override val data: StateFlow<State<Boolean, Throwable>> get() = _data.asStateFlow()

    override suspend fun updateAll(passwords: List<PasswordUseCaseModel>) {
        try {
            passwords.forEach { passwordRepository.update(it.toPassword()) }

            _data.emit(State.Success(true))
        } catch (e: Throwable) {
            _data.emit(State.Failure(e))
        }
    }
}