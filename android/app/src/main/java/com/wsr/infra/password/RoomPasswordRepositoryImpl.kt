package com.wsr.infra.password

import com.wsr.exceptions.DeleteDataFailedException
import com.wsr.exceptions.GetAllDataFailedException
import com.wsr.exceptions.UpdateDataFailedException
import com.wsr.password.Password
import com.wsr.password.PasswordId
import com.wsr.password.PasswordRepository
import com.wsr.passwordgroup.PasswordGroupId

class RoomPasswordRepositoryImpl(private val passwordEntityDao: PasswordEntityDao) :
    PasswordRepository {
    override suspend fun getAllByPasswordGroupId(passwordGroupId: PasswordGroupId): List<Password> =
        try {
            passwordEntityDao.getAllByPasswordGroupId(passwordGroupId.value).map { it.toPassword() }
        } catch (e: Exception) {
            throw GetAllDataFailedException.DatabaseException(e.message ?: "")
        }

    override suspend fun upsert(password: Password): Password = try {
        passwordEntityDao.upsert(password.toEntity())
        password
    } catch (e: Exception) {
        throw UpdateDataFailedException.DatabaseException(e.message ?: "")
    }

    override suspend fun delete(id: PasswordId) = try {
        passwordEntityDao.delete(id.value)
    } catch (e: Exception) {
        throw DeleteDataFailedException.DatabaseException(e.message ?: "")
    }
}
