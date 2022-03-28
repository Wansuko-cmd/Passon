package com.wsr.fetch

import com.wsr.PasswordGroupUseCaseModel
import com.wsr.email.Email
import com.wsr.exceptions.GetAllDataFailedException

interface FetchAllPasswordGroupUseCaseQueryService {
    @Throws(GetAllDataFailedException::class)
    suspend fun getAllPasswordGroup(email: Email): List<PasswordGroupUseCaseModel>
}
