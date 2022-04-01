package com.wsr.fetch

import com.wsr.PasswordPairUseCaseModel
import com.wsr.maybe.Maybe
import kotlinx.coroutines.flow.Flow

interface FetchPasswordPairUseCase {
    val data: Flow<Maybe<PasswordPairUseCaseModel, FetchPasswordPairUseCaseException>>
    suspend fun fetch(passwordGroupId: String)
}
