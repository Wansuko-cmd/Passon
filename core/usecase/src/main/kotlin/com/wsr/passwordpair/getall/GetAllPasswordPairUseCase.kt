package com.wsr.passwordpair.getall

import com.wsr.exceptions.GetAllDataFailedException
import com.wsr.passwordpair.PasswordPairUseCaseModel
import com.wsr.state.State
import kotlinx.coroutines.flow.Flow

interface GetAllPasswordPairUseCase {
    val data: Flow<State<List<PasswordPairUseCaseModel>, GetAllDataFailedException>>

    suspend fun getAllByPasswordGroupId(passwordGroupId: String)
}
