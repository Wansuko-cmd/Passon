package com.wsr.fetch

import com.wsr.PasswordGroupUseCaseModel
import com.wsr.exceptions.GetAllDataFailedException
import com.wsr.state.State
import kotlinx.coroutines.flow.Flow

interface FetchAllPasswordGroupUseCase {
    val data: Flow<State<List<PasswordGroupUseCaseModel>, GetAllDataFailedException>>
    suspend fun fetch(email: String)
}
