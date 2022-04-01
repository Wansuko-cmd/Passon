package com.wsr.create

import com.wsr.PasswordGroupUseCaseModel
import com.wsr.maybe.Maybe

interface CreatePasswordGroupUseCase {
    suspend fun create(
        email: String,
        title: String,
    ): Maybe<PasswordGroupUseCaseModel, CreatePasswordGroupUseCaseException>
}
