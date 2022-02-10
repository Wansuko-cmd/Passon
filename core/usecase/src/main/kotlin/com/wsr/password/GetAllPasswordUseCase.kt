package com.wsr.password

import com.wsr.exceptions.GetAllException
import com.wsr.state.State
import kotlinx.coroutines.flow.StateFlow

interface GetAllPasswordUseCase {
    val data: StateFlow<State<List<PasswordUseCaseModel>, GetAllException>>

    suspend fun getAllByPasswordGroupId(passwordGroupId: String)
}