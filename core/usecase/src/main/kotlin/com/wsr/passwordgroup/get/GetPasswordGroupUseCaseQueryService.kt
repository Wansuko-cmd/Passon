package com.wsr.passwordgroup.get

import com.wsr.exceptions.GetDataFailedException
import com.wsr.passwordgroup.PasswordGroup
import com.wsr.passwordgroup.PasswordGroupId

interface GetPasswordGroupUseCaseQueryService {
    @Throws(GetDataFailedException::class)
    suspend fun getById(passwordGroupId: PasswordGroupId): PasswordGroup
}
