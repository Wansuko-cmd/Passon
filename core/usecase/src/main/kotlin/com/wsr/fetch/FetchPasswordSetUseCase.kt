package com.wsr.fetch

import com.wsr.PasswordGroupUseCaseModel
import com.wsr.PasswordItemUseCaseModel
import com.wsr.exceptions.GetAllDataFailedException
import com.wsr.state.State
import kotlinx.coroutines.flow.Flow

interface FetchPasswordSetUseCase {
    val data: Flow<State<Pair<PasswordGroupUseCaseModel, List<PasswordItemUseCaseModel>>, GetAllDataFailedException>>
    suspend fun fetch(passwordGroupId: String)
}
