package com.wsr.password.updateall

import com.wsr.exceptions.UpdateDataFailedException
import com.wsr.password.PasswordUseCaseModel
import com.wsr.state.State

interface UpdateAllPasswordUseCase {
    suspend fun updateAll(passwords: List<PasswordUseCaseModel>): State<Boolean, UpdateDataFailedException>
}