package com.wsr.passworditem.getall

import com.wsr.email.Email
import com.wsr.exceptions.GetAllDataFailedException
import com.wsr.passwordgroup.PasswordGroup
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passworditem.PasswordItem

interface GetAllPasswordItemQueryService {
    @Throws(GetAllDataFailedException::class)
    suspend fun getAllByPasswordGroupId(passwordGroupId: PasswordGroupId): List<PasswordItem>
}