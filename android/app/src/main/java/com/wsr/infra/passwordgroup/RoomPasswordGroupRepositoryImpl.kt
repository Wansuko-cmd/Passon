package com.wsr.infra.passwordgroup

import com.wsr.email.Email
import com.wsr.exceptions.CreateDataFailedException
import com.wsr.exceptions.DeleteDataFailedException
import com.wsr.exceptions.GetAllDataFailedException
import com.wsr.exceptions.GetDataFailedException
import com.wsr.exceptions.UpdateDataFailedException
import com.wsr.passwordgroup.PasswordGroup
import com.wsr.passwordgroup.PasswordGroupRepository
import com.wsr.utils.UniqueId

class RoomPasswordGroupRepositoryImpl(private val passwordGroupEntityDao: PasswordGroupEntityDao) :
    PasswordGroupRepository {
    override suspend fun getAllByEmail(email: Email): List<PasswordGroup> = try {
        passwordGroupEntityDao.getAllByEmail(email.value).map { it.toPasswordGroup() }
    } catch (e: Exception) {
        throw GetAllDataFailedException.DatabaseException(e.message ?: "")
    }

    override suspend fun getById(id: UniqueId): PasswordGroup = try {
        passwordGroupEntityDao.getById(id.value).toPasswordGroup()
    } catch (e: NullPointerException) {
        throw GetDataFailedException.NoSuchElementException(e.message ?: "")
    } catch (e: Exception) {
        throw GetDataFailedException.DatabaseException(e.message ?: "")
    }

    override suspend fun create(passwordGroup: PasswordGroup) = try {
        passwordGroupEntityDao.insert(passwordGroup.toEntity())
    } catch (e: Exception) {
        throw CreateDataFailedException.DatabaseException(e.message ?: "")
    }

    override suspend fun update(id: UniqueId, title: String, remark: String) = try {
        passwordGroupEntityDao.update(id.value, title, remark)
    } catch (e: Exception) {
        throw UpdateDataFailedException.DatabaseException(e.message ?: "")
    }

    override suspend fun delete(id: UniqueId) = try {
        passwordGroupEntityDao.delete(id.value)
    } catch (e: Exception) {
        throw DeleteDataFailedException.DatabaseException(e.message ?: "")
    }
}
