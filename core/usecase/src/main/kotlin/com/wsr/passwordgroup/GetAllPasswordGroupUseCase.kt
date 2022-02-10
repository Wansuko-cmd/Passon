package com.wsr.passwordgroup

import com.wsr.exceptions.GetAllException
import com.wsr.state.State
import kotlinx.coroutines.flow.StateFlow

interface GetAllPasswordGroupUseCase {
    val data: StateFlow<State<List<PasswordGroupUseCaseModel>, GetAllException>>

    suspend fun getAllByEmail(email: String)
}