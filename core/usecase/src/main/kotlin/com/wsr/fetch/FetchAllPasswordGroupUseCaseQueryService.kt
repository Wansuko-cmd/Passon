package com.wsr.fetch

import com.wsr.PasswordGroupUseCaseModel
import com.wsr.exceptions.GetAllDataFailedException
import com.wsr.user.Email

interface FetchAllPasswordGroupUseCaseQueryService {
    @Throws(GetAllDataFailedException::class)
    suspend fun getAllPasswordGroup(email: Email): List<PasswordGroupUseCaseModel>
}
