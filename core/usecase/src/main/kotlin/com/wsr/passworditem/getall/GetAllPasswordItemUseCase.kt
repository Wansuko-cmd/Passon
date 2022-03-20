package com.wsr.passworditem.getall

import com.wsr.exceptions.GetAllDataFailedException
import com.wsr.passworditem.PasswordItemUseCaseModel
import com.wsr.state.State
import kotlinx.coroutines.flow.Flow

interface GetAllPasswordItemUseCase {
    val data: Flow<State<List<PasswordItemUseCaseModel>, GetAllDataFailedException>>

    suspend fun getAllByPasswordGroupId(passwordGroupId: String)
}
