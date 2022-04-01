package com.wsr.delete

import com.wsr.maybe.Maybe

interface DeletePasswordGroupUseCase {
    suspend fun delete(id: String): Maybe<Unit, DeletePasswordGroupUseCaseException>
}
