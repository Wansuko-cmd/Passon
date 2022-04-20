package com.wsr.create

import com.wsr.PasswordGroupUseCaseModel
import com.wsr.maybe.Maybe

interface CreatePasswordGroupUseCase {
    suspend fun create(
        userId: String,
        title: String,
    ): Maybe<PasswordGroupUseCaseModel, CreatePasswordGroupUseCaseException>
}

sealed class CreatePasswordGroupUseCaseException : Throwable() {
}
