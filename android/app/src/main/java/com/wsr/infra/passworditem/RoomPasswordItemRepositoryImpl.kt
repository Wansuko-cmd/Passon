package com.wsr.infra.passworditem

import com.wsr.exceptions.DeleteDataFailedException
import com.wsr.exceptions.GetAllDataFailedException
import com.wsr.exceptions.UpdateDataFailedException
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passworditem.PasswordItem
import com.wsr.passworditem.PasswordItemId
import com.wsr.passworditem.PasswordItemRepository

class RoomPasswordItemRepositoryImpl(private val passwordEntityDao: PasswordItemEntityDao) :
    PasswordItemRepository {
    override suspend fun getAllByPasswordGroupId(passwordGroupId: PasswordGroupId): List<PasswordItem> =
        try {
            passwordEntityDao.getAllByPasswordGroupId(passwordGroupId.value).map { it.toPassword() }
        } catch (e: Exception) {
            throw GetAllDataFailedException.DatabaseException(e.message ?: "")
        }

    override suspend fun upsert(passwordItem: PasswordItem) = try {
        passwordEntityDao.upsert(passwordItem.toEntity())
    } catch (e: Exception) {
        throw UpdateDataFailedException.DatabaseException(e.message ?: "")
    }

    override suspend fun delete(id: PasswordItemId) = try {
        passwordEntityDao.delete(id.value)
    } catch (e: Exception) {
        throw DeleteDataFailedException.DatabaseException(e.message ?: "")
    }
}
