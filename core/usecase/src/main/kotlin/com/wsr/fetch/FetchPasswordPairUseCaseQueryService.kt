package com.wsr.fetch

import com.wsr.PasswordPairUseCaseModel
import com.wsr.exceptions.GetAllDataFailedException
import com.wsr.passwordgroup.PasswordGroupId
import kotlin.jvm.Throws

interface FetchPasswordPairUseCaseQueryService {
    @Throws(GetAllDataFailedException::class)
    suspend fun getPasswordPair(passwordGroupId: PasswordGroupId): PasswordPairUseCaseModel
}
