package com.wsr.fetch

import com.wsr.email.Email
import com.wsr.exceptions.GetAllDataFailedException
import com.wsr.passwordgroup.PasswordGroup

interface FetchAllPasswordGroupUseCaseQueryService {
    @Throws(GetAllDataFailedException::class)
    suspend fun getAllPasswordGroup(email: Email): List<PasswordGroup>
}
