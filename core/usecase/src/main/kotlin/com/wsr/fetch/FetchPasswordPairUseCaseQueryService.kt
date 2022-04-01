package com.wsr.fetch

import com.wsr.PasswordPairUseCaseModel
import com.wsr.passwordgroup.PasswordGroupId

interface FetchPasswordPairUseCaseQueryService {
    suspend fun getPasswordPair(passwordGroupId: PasswordGroupId): PasswordPairUseCaseModel
}
