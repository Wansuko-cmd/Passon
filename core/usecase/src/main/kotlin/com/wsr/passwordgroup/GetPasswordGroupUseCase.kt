package com.wsr.passwordgroup

import com.wsr.state.State
import com.wsr.user.Email
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class GetPasswordGroupUseCase(
    private val passwordGroupRepository: PasswordGroupRepository
) {

    private val _data =
        MutableStateFlow<State<List<PasswordGroupUseCaseModel>, Throwable>>(State.Loading)
    val data get() = _data.asStateFlow()

    suspend fun getAllByEmail(email: String) {
        val passwordGroup = passwordGroupRepository
            .getAllByEmail(Email(email))
            .map { it.toUseCaseModel() }

        _data.emit(State.Success(passwordGroup))
    }
}
