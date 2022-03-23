package com.wsr.passworditem.getall

import com.wsr.exceptions.GetAllDataFailedException
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passworditem.PasswordItemRepository
import com.wsr.passworditem.PasswordItemUseCaseModel
import com.wsr.passworditem.toUseCaseModel
import com.wsr.state.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class GetAllPasswordItemUseCaseImpl(
    private val getAllPasswordItemQueryService: GetAllPasswordItemQueryService,
) : GetAllPasswordItemUseCase {

    private val _data =
        MutableStateFlow<State<List<PasswordItemUseCaseModel>, GetAllDataFailedException>>(State.Loading)
    override val data get() = _data.asStateFlow()

    override suspend fun getAllByPasswordGroupId(passwordGroupId: String) {
        try {
            _data.emit(State.Loading)
            val passwords = getAllPasswordItemQueryService
                .getAllByPasswordGroupId(PasswordGroupId(passwordGroupId))
                .map { it.toUseCaseModel() }

            _data.emit(State.Success(passwords))
        } catch (e: GetAllDataFailedException) {
            _data.emit(State.Failure(e))
        }
    }
}
