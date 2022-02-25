package com.wsr.password.getall

import com.wsr.exceptions.GetAllDataFailedException
import com.wsr.password.PasswordUseCaseModel
import com.wsr.state.State
import kotlinx.coroutines.flow.StateFlow

interface GetAllPasswordUseCase {
    val data: StateFlow<State<List<PasswordUseCaseModel>, GetAllDataFailedException>>

    suspend fun getAllByPasswordGroupId(passwordGroupId: String)
}