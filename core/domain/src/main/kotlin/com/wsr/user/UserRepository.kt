package com.wsr.user

import com.wsr.exceptions.CreateDataFailedException
import com.wsr.exceptions.UpdateDataFailedException
import com.wsr.state.State

interface UserRepository {
    suspend fun create(user: User): State<Unit, CreateDataFailedException>
    suspend fun update(user: User): State<Unit, UpdateDataFailedException>
}
