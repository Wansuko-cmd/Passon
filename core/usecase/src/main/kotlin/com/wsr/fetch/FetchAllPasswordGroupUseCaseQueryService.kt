package com.wsr.fetch

import com.wsr.PasswordGroupUseCaseModel
import com.wsr.state.State
import com.wsr.user.Email

interface FetchAllPasswordGroupUseCaseQueryService {
    suspend fun getAllPasswordGroup(email: Email): State<List<PasswordGroupUseCaseModel>, FetchAllPasswordGroupUseCaseQueryServiceException>
}

sealed class FetchAllPasswordGroupUseCaseQueryServiceException : Throwable() {
    class DatabaseError(override val message: String) : FetchAllPasswordGroupUseCaseException()
}
