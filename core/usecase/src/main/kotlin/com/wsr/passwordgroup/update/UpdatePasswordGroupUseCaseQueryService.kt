package com.wsr.passwordgroup.update

import com.wsr.exceptions.GetDataFailedException
import com.wsr.passwordgroup.PasswordGroup
import com.wsr.passwordgroup.PasswordGroupId

interface UpdatePasswordGroupUseCaseQueryService {
    @Throws(GetDataFailedException::class)
    suspend fun getById(passwordGroupId: PasswordGroupId): PasswordGroup
}