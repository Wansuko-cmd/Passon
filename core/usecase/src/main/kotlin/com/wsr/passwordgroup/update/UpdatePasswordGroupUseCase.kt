package com.wsr.passwordgroup.update

import com.wsr.exceptions.UpdateDataFailedException
import com.wsr.state.State

interface UpdatePasswordGroupUseCase {
    suspend fun update(
        id: String,
        title: String? = null,
        remark: String? = null,
    ): State<Boolean, UpdateDataFailedException>
}