package com.wsr.auth

import com.wsr.exceptions.GetDataFailedException
import com.wsr.state.State

interface LoginUseCase {
    suspend fun shouldPass(password: String): State<Boolean, GetDataFailedException>
}
