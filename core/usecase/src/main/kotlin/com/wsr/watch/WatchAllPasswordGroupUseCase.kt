package com.wsr.watch

import com.wsr.exceptions.GetAllDataFailedException
import com.wsr.passwordgroup.PasswordGroupUseCaseModel
import com.wsr.state.State
import kotlinx.coroutines.flow.StateFlow

interface WatchAllPasswordGroupUseCase {
    val data: StateFlow<State<List<PasswordGroupUseCaseModel>, GetAllDataFailedException>>
    suspend fun fetch()
}
