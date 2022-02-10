package com.wsr.passwordgroup

import com.wsr.exceptions.GetAllException
import com.wsr.state.State
import com.wsr.user.Email
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class GetAllPasswordGroupUseCaseImpl(
    private val passwordGroupRepository: PasswordGroupRepository
) : GetAllPasswordGroupUseCase {

    private val _data =
        MutableStateFlow<State<List<PasswordGroupUseCaseModel>, GetAllException>>(State.Loading)
    override val data get() = _data.asStateFlow()

    override suspend fun getAllByEmail(email: String) {
        try {
            val passwordGroups = passwordGroupRepository
                .getAllByEmail(Email(email))
                .map { it.toUseCaseModel() }

            _data.emit(State.Success(passwordGroups))

        } catch (e: GetAllException) {
            _data.emit(State.Failure(e))
        }
    }
}
