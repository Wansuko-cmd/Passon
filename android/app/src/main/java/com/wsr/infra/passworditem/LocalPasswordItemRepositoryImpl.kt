package com.wsr.infra.passworditem

import com.wsr.exceptions.DeleteDataFailedException
import com.wsr.exceptions.UpsertDataFailedException
import com.wsr.maybe.Maybe
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passworditem.PasswordItem
import com.wsr.passworditem.PasswordItemId
import com.wsr.passworditem.PasswordItemRepository

class LocalPasswordItemRepositoryImpl(private val passwordEntityDao: PasswordItemEntityDao) :
    PasswordItemRepository {

    override suspend fun upsert(passwordItem: PasswordItem) = try {
        passwordEntityDao.upsert(passwordItem.toEntity())
        Maybe.Success(Unit)
    } catch (e: Exception) {
        Maybe.Failure(UpsertDataFailedException.DatabaseException(e.message ?: ""))
    }

    override suspend fun delete(id: PasswordItemId) = try {
        passwordEntityDao.delete(id.value)
        Maybe.Success(Unit)
    } catch (e: Exception) {
        Maybe.Failure(DeleteDataFailedException.DatabaseException(e.message ?: ""))
    }

    override suspend fun deleteAll(passwordGroupId: PasswordGroupId) = try {
        passwordEntityDao.deleteAllByPasswordGroupId(passwordGroupId.value)
        Maybe.Success(Unit)
    } catch (e: Exception) {
        Maybe.Failure(DeleteDataFailedException.DatabaseException(e.message ?: ""))
    }
}
