package com.wsr.passwordgroup

import com.wsr.state.State
import com.wsr.user.Email
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class GetPasswordGroupUseCaseImpl(
    private val passwordGroupRepository: PasswordGroupRepository
) : GetPasswordGroupUseCase {

    private val _data =
        MutableStateFlow<State<List<PasswordGroupUseCaseModel>, Throwable>>(State.Loading)
    override val data get() = _data.asStateFlow()

    override suspend fun getAllByEmail(email: String) {
        val passwordGroup = passwordGroupRepository
            .getAllByEmail(Email(email))
            .map { it.toUseCaseModel() }

        _data.emit(State.Success(passwordGroup))
    }
}
