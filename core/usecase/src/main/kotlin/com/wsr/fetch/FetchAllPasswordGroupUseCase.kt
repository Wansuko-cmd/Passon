package com.wsr.fetch

import com.wsr.PasswordGroupUseCaseModel
import com.wsr.maybe.Maybe
import kotlinx.coroutines.flow.Flow

interface FetchAllPasswordGroupUseCase {
    val data: Flow<Maybe<List<PasswordGroupUseCaseModel>, FetchAllPasswordGroupUseCaseException>>
    suspend fun fetch(email: String)
}
