package com.wsr.fetch

import com.wsr.PasswordPairUseCaseModel
import com.wsr.state.State
import kotlinx.coroutines.flow.Flow

interface FetchPasswordPairUseCase {
    val data: Flow<State<PasswordPairUseCaseModel, FetchPasswordPairUseCaseException>>
    suspend fun fetch(passwordGroupId: String)
}
