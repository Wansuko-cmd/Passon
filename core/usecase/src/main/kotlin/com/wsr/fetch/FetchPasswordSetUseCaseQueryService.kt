package com.wsr.fetch

import com.wsr.exceptions.GetAllDataFailedException
import com.wsr.passwordgroup.PasswordGroup
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passworditem.PasswordItem
import kotlin.jvm.Throws

interface FetchPasswordSetUseCaseQueryService {
    @Throws(GetAllDataFailedException::class)
    suspend fun get(passwordGroupId: PasswordGroupId): Pair<PasswordGroup, List<PasswordItem>>
}
