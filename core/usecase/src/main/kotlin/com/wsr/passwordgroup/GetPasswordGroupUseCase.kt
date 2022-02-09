package com.wsr.passwordgroup

import com.wsr.state.State
import kotlinx.coroutines.flow.StateFlow

interface GetPasswordGroupUseCase {
    val data: StateFlow<State<List<PasswordGroupUseCaseModel>, Throwable>>

    suspend fun getAllByEmail(email: String)
}