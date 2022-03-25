package com.wsr.passworditem.getall

import com.wsr.exceptions.GetAllDataFailedException
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passworditem.PasswordItem

interface GetAllPasswordItemUseCaseQueryService {
    @Throws(GetAllDataFailedException::class)
    suspend fun getAllByPasswordGroupId(passwordGroupId: PasswordGroupId): List<PasswordItem>
}
