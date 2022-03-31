package com.wsr.fetch

import com.wsr.PasswordGroupUseCaseModel
import com.wsr.state.State
import kotlinx.coroutines.flow.Flow

interface FetchAllPasswordGroupUseCase {
    val data: Flow<State<List<PasswordGroupUseCaseModel>, FetchAllPasswordGroupUseCaseException>>
    suspend fun fetch(email: String)
}
