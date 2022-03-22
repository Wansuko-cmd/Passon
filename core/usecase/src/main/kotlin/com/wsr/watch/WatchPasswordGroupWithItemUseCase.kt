package com.wsr.watch

import com.wsr.exceptions.GetAllDataFailedException
import com.wsr.passwordgroup.PasswordGroupUseCaseModel
import com.wsr.passworditem.PasswordItemUseCaseModel
import com.wsr.state.State
import kotlinx.coroutines.flow.StateFlow

interface WatchPasswordGroupWithItemUseCase {
    val data: StateFlow<State<Pair<PasswordGroupUseCaseModel, List<PasswordItemUseCaseModel>>, GetAllDataFailedException>>
    suspend fun fetch()
}