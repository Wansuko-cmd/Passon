package com.wsr.delete

import com.wsr.state.State

interface DeletePasswordGroupUseCase {
    suspend fun delete(id: String): State<Unit, DeletePasswordGroupUseCaseException>
}
