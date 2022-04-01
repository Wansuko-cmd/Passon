package com.wsr.infra.passworditem

import com.wsr.exceptions.DeleteDataFailedException
import com.wsr.exceptions.UpsertDataFailedException
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passworditem.PasswordItem
import com.wsr.passworditem.PasswordItemId
import com.wsr.passworditem.PasswordItemRepository
import com.wsr.state.State

class LocalPasswordItemRepositoryImpl(private val passwordEntityDao: PasswordItemEntityDao) :
    PasswordItemRepository {

    override suspend fun upsert(passwordItem: PasswordItem) = try {
        passwordEntityDao.upsert(passwordItem.toEntity())
        State.Success(Unit)
    } catch (e: Exception) {
        State.Failure(UpsertDataFailedException.DatabaseException(e.message ?: ""))
    }

    override suspend fun delete(id: PasswordItemId) = try {
        passwordEntityDao.delete(id.value)
        State.Success(Unit)
    } catch (e: Exception) {
        State.Failure(DeleteDataFailedException.DatabaseException(e.message ?: ""))
    }

    override suspend fun deleteAll(passwordGroupId: PasswordGroupId) = try {
        passwordEntityDao.deleteAllByPasswordGroupId(passwordGroupId.value)
        State.Success(Unit)
    } catch (e: Exception) {
        State.Failure(DeleteDataFailedException.DatabaseException(e.message ?: ""))
    }
}
