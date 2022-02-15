package com.wsr.passwordgroup.getall

import com.wsr.exceptions.GetAllDataFailedException
import com.wsr.passwordgroup.PasswordGroupRepository
import com.wsr.passwordgroup.PasswordGroupUseCaseModel
import com.wsr.passwordgroup.toUseCaseModel
import com.wsr.state.State
import com.wsr.user.Email
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class GetAllPasswordGroupUseCaseImpl(
    private val passwordGroupRepository: PasswordGroupRepository
) : GetAllPasswordGroupUseCase {

    private val _data =
        MutableStateFlow<State<List<PasswordGroupUseCaseModel>, GetAllDataFailedException>>(State.Loading)
    override val data get() = _data.asStateFlow()

    override suspend fun getAllByEmail(email: String) {
        try {
            val passwordGroups = passwordGroupRepository
                .getAllByEmail(Email(email))
                .map { it.toUseCaseModel() }

            _data.emit(State.Success(passwordGroups))

        } catch (e: GetAllDataFailedException) {
            _data.emit(State.Failure(e))
        }
    }
}
