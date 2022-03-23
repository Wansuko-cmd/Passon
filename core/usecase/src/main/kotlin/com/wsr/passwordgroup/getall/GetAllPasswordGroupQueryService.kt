package com.wsr.passwordgroup.getall

import com.wsr.email.Email
import com.wsr.exceptions.GetAllDataFailedException
import com.wsr.passwordgroup.PasswordGroup

interface GetAllPasswordGroupQueryService {
    @Throws(GetAllDataFailedException::class)
    suspend fun getAllByEmail(email: Email): List<PasswordGroup>
}