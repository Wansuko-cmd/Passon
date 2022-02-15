package com.wsr.passwordgroup.getall

import com.wsr.exceptions.GetAllDataFailedException
import com.wsr.passwordgroup.PasswordGroupUseCaseModel
import com.wsr.state.State
import kotlinx.coroutines.flow.StateFlow

interface GetAllPasswordGroupUseCase {
    val data: StateFlow<State<List<PasswordGroupUseCaseModel>, GetAllDataFailedException>>

    suspend fun getAllByEmail(email: String)
}