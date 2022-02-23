package com.wsr.password.updateall

import com.wsr.password.PasswordUseCaseModel
import com.wsr.state.State
import kotlinx.coroutines.flow.StateFlow

interface UpdateAllPasswordUseCase {
    val data: StateFlow<State<Boolean, Throwable>>

    suspend fun updateAll(passwords: List<PasswordUseCaseModel>)
}