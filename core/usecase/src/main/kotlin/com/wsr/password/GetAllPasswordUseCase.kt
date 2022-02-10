package com.wsr.password

import com.wsr.exceptions.GetAllDataFailedException
import com.wsr.state.State
import kotlinx.coroutines.flow.StateFlow

interface GetAllPasswordUseCase {
    val data: StateFlow<State<List<PasswordUseCaseModel>, GetAllDataFailedException>>

    suspend fun getAllByPasswordGroupId(passwordGroupId: String)
}