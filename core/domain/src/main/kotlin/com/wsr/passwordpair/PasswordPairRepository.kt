package com.wsr.passwordpair

import com.wsr.exceptions.DeleteDataFailedException
import com.wsr.exceptions.GetAllDataFailedException
import com.wsr.exceptions.UpsertDataFailedException
import com.wsr.passwordgroup.PasswordGroupId

interface PasswordPairRepository {
    @Throws(GetAllDataFailedException::class)
    suspend fun getAllByPasswordGroupId(passwordGroupId: PasswordGroupId): List<PasswordPair>

    @Throws(UpsertDataFailedException::class)
    suspend fun upsert(passwordPair: PasswordPair): PasswordPair

    @Throws(DeleteDataFailedException::class)
    suspend fun delete(id: PasswordPairId)
}
