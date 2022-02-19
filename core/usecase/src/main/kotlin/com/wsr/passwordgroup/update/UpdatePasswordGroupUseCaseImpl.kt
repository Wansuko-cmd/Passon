package com.wsr.passwordgroup.update

import com.wsr.passwordgroup.PasswordGroupRepository
import com.wsr.state.State
import com.wsr.ext.UniqueId
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class UpdatePasswordGroupUseCaseImpl(
    private val passwordGroupRepository: PasswordGroupRepository
) : UpdatePasswordGroupUseCase {
    private val _data = MutableStateFlow<State<Boolean, Throwable>>(State.Loading)
    override val data get() = _data.asStateFlow()

    override suspend fun update(id: String, title: String?, remark: String?) {
        try {
            passwordGroupRepository.update(UniqueId(id), title, remark)

            _data.emit(State.Success(true))
        } catch (e: Throwable) {
            _data.emit(State.Failure(e))
        }
    }
}