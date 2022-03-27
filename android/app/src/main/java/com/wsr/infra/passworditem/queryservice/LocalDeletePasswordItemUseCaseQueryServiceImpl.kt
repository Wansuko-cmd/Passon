package com.wsr.infra.passworditem.queryservice

import com.wsr.exceptions.GetAllDataFailedException
import com.wsr.exceptions.GetDataFailedException
import com.wsr.infra.passworditem.PasswordItemEntityDao
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passworditem.PasswordItem
import com.wsr.passworditem.PasswordItemId
import com.wsr.passworditem.delete.DeletePasswordItemUseCaseQueryService

class LocalDeletePasswordItemUseCaseQueryServiceImpl(
    private val passwordEntityDao: PasswordItemEntityDao,
) : DeletePasswordItemUseCaseQueryService {
    override suspend fun getById(passwordItemId: PasswordItemId): PasswordItem = try {
        passwordEntityDao.getById(passwordItemId.value).toPassword()
    } catch (e: Exception) {
        throw GetDataFailedException.DatabaseException(e.message ?: "")
    }

    override suspend fun getAllByPasswordGroupId(passwordGroupId: PasswordGroupId): List<PasswordItem> = try {
        passwordEntityDao.getAllByPasswordGroupId(passwordGroupId.value).map { it.toPassword() }
    } catch (e: Exception) {
        throw GetAllDataFailedException.DatabaseException(e.message ?: "")
    }
}
