package com.wsr.passwordgroup.getall

import com.wsr.email.Email
import com.wsr.exceptions.GetAllDataFailedException
import com.wsr.passwordgroup.PasswordGroupRepository
import com.wsr.passwordgroup.PasswordGroupUseCaseModel
import com.wsr.passwordgroup.toUseCaseModel
import com.wsr.state.State
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged

class GetAllPasswordGroupUseCaseImpl(
    private val passwordGroupRepository: PasswordGroupRepository,
) : GetAllPasswordGroupUseCase {

    private val _data =
        MutableSharedFlow<State<List<PasswordGroupUseCaseModel>, GetAllDataFailedException>>(replay = 0)
    override val data get() = _data.asSharedFlow().distinctUntilChanged()

    override suspend fun getAllByEmail(email: String) {
        try {
            _data.emit(State.Loading)
            val passwordGroups = passwordGroupRepository
                .getAllByEmail(Email.of(email))
                .map { it.toUseCaseModel() }

            _data.emit(State.Success(passwordGroups))
        } catch (e: GetAllDataFailedException) {
            _data.emit(State.Failure(e))
        }
    }
}
