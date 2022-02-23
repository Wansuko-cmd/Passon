package com.wsr.passwordgroup.update

import com.wsr.state.State
import kotlinx.coroutines.flow.StateFlow

interface UpdatePasswordGroupUseCase {
    val data: StateFlow<State<Boolean, Throwable>>

    suspend fun update(id: String, title: String? = null, remark: String? = null)
}