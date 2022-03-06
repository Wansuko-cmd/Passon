package com.wsr.passwordgroup.get

import com.wsr.exceptions.GetDataFailedException
import com.wsr.passwordgroup.PasswordGroupUseCaseModel
import com.wsr.state.State
import kotlinx.coroutines.flow.Flow

interface GetPasswordGroupUseCase {
    val data: Flow<State<PasswordGroupUseCaseModel, GetDataFailedException>>

    suspend fun getById(id: String)
}