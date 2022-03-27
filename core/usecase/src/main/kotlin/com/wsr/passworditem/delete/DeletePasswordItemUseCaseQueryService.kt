package com.wsr.passworditem.delete

import com.wsr.exceptions.GetAllDataFailedException
import com.wsr.exceptions.GetDataFailedException
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passworditem.PasswordItem
import com.wsr.passworditem.PasswordItemId

interface DeletePasswordItemUseCaseQueryService {
    @Throws(GetDataFailedException::class)
    suspend fun getById(passwordItemId: PasswordItemId): PasswordItem

    @Throws(GetAllDataFailedException::class)
    suspend fun getAllByPasswordGroupId(passwordGroupId: PasswordGroupId): List<PasswordItem>
}
