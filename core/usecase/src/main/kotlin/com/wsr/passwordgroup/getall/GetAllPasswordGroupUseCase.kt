package com.wsr.passwordgroup.getall

import com.wsr.exceptions.GetAllDataFailedException
import com.wsr.passwordgroup.PasswordGroupUseCaseModel
import com.wsr.state.State
import kotlinx.coroutines.flow.Flow

interface GetAllPasswordGroupUseCase {
    val data: Flow<State<List<PasswordGroupUseCaseModel>, GetAllDataFailedException>>

    suspend fun getAllByEmail(email: String)
}