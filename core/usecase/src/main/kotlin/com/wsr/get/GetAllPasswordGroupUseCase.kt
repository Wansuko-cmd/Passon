package com.wsr.get

import com.wsr.PasswordGroupUseCaseModel
import com.wsr.maybe.Maybe

interface GetAllPasswordGroupUseCase {
    suspend fun get(email: String): Maybe<List<PasswordGroupUseCaseModel>, GetAllPasswordGroupUseCaseException>
}