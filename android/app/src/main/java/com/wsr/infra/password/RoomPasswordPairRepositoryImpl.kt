package com.wsr.infra.password

import com.wsr.exceptions.DeleteDataFailedException
import com.wsr.exceptions.GetAllDataFailedException
import com.wsr.exceptions.UpdateDataFailedException
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passwordpair.PasswordPair
import com.wsr.passwordpair.PasswordPairId
import com.wsr.passwordpair.PasswordPairRepository

class RoomPasswordPairRepositoryImpl(private val passwordEntityDao: PasswordPairEntityDao) :
    PasswordPairRepository {
    override suspend fun getAllByPasswordGroupId(passwordGroupId: PasswordGroupId): List<PasswordPair> =
        try {
            passwordEntityDao.getAllByPasswordGroupId(passwordGroupId.value).map { it.toPassword() }
        } catch (e: Exception) {
            throw GetAllDataFailedException.DatabaseException(e.message ?: "")
        }

    override suspend fun upsert(passwordPair: PasswordPair): PasswordPair = try {
        passwordEntityDao.upsert(passwordPair.toEntity())
        passwordPair
    } catch (e: Exception) {
        throw UpdateDataFailedException.DatabaseException(e.message ?: "")
    }

    override suspend fun delete(id: PasswordPairId) = try {
        passwordEntityDao.delete(id.value)
    } catch (e: Exception) {
        throw DeleteDataFailedException.DatabaseException(e.message ?: "")
    }
}
