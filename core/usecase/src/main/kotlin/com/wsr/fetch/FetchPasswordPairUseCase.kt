package com.wsr.fetch

import com.wsr.PasswordPairUseCaseModel
import com.wsr.exceptions.GetAllDataFailedException
import com.wsr.state.State
import kotlinx.coroutines.flow.Flow

interface FetchPasswordPairUseCase {
    val data: Flow<State<PasswordPairUseCaseModel, GetAllDataFailedException>>
    suspend fun fetch(passwordGroupId: String)
}
