package com.wsr.fetch

import com.wsr.PasswordGroupUseCaseModel
import com.wsr.user.Email

interface FetchAllPasswordGroupUseCaseQueryService {
    suspend fun getAllPasswordGroup(email: Email): List<PasswordGroupUseCaseModel>
}
