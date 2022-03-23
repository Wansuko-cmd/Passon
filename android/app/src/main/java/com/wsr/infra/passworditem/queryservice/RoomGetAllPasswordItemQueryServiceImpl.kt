package com.wsr.infra.passworditem.queryservice

import com.wsr.exceptions.GetAllDataFailedException
import com.wsr.infra.passworditem.PasswordItemEntityDao
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passworditem.PasswordItem
import com.wsr.passworditem.getall.GetAllPasswordItemQueryService

class RoomGetAllPasswordItemQueryServiceImpl(
    private val passwordEntityDao: PasswordItemEntityDao,
)  : GetAllPasswordItemQueryService {
    override suspend fun getAllByPasswordGroupId(passwordGroupId: PasswordGroupId): List<PasswordItem> =
        try {
            passwordEntityDao.getAllByPasswordGroupId(passwordGroupId.value).map { it.toPassword() }
        } catch (e: Exception) {
            throw GetAllDataFailedException.DatabaseException(e.message ?: "")
        }
}