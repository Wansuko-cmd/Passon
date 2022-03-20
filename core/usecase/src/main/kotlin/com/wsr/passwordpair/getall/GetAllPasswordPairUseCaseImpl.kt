package com.wsr.passwordpair.getall

import com.wsr.exceptions.GetAllDataFailedException
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passwordpair.PasswordPairRepository
import com.wsr.passwordpair.PasswordPairUseCaseModel
import com.wsr.passwordpair.toUseCaseModel
import com.wsr.state.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class GetAllPasswordPairUseCaseImpl(
    private val passwordRepository: PasswordPairRepository,
) : GetAllPasswordPairUseCase {

    private val _data =
        MutableStateFlow<State<List<PasswordPairUseCaseModel>, GetAllDataFailedException>>(State.Loading)
    override val data get() = _data.asStateFlow()

    override suspend fun getAllByPasswordGroupId(passwordGroupId: String) {
        try {
            _data.emit(State.Loading)
            val passwords = passwordRepository
                .getAllByPasswordGroupId(PasswordGroupId(passwordGroupId))
                .map { it.toUseCaseModel() }

            _data.emit(State.Success(passwords))
        } catch (e: GetAllDataFailedException) {
            _data.emit(State.Failure(e))
        }
    }
}
