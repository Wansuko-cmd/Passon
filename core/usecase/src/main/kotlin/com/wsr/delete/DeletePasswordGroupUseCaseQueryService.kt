package com.wsr.delete

import com.wsr.exceptions.GetDataFailedException
import com.wsr.passwordgroup.PasswordGroup
import com.wsr.passwordgroup.PasswordGroupId

interface DeletePasswordGroupUseCaseQueryService {
    @Throws(GetDataFailedException::class)
    suspend fun getPasswordGroup(passwordGroupId: PasswordGroupId): PasswordGroup
}
