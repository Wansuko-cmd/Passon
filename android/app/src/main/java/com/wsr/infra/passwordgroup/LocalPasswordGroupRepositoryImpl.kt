package com.wsr.infra.passwordgroup

import com.wsr.exceptions.CreateDataFailedException
import com.wsr.exceptions.DeleteDataFailedException
import com.wsr.exceptions.UpdateDataFailedException
import com.wsr.passwordgroup.PasswordGroup
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passwordgroup.PasswordGroupRepository

class LocalPasswordGroupRepositoryImpl(private val passwordGroupEntityDao: PasswordGroupEntityDao) :
    PasswordGroupRepository {

    override suspend fun create(passwordGroup: PasswordGroup) = try {
        passwordGroupEntityDao.insert(passwordGroup.toEntity())
    } catch (e: Exception) {
        throw CreateDataFailedException.DatabaseException(e.message ?: "")
    }

    override suspend fun update(id: PasswordGroupId, title: String, remark: String) =
        try {
            passwordGroupEntityDao.getById(id.value)
                .copyWithTitle(title)
                .copyWithRemark(remark)
                .let { passwordGroupEntityDao.update(it) }
        } catch (e: Exception) {
            throw UpdateDataFailedException.DatabaseException(e.message ?: "")
        }

    override suspend fun delete(id: PasswordGroupId) = try {
        passwordGroupEntityDao.delete(id.value)
    } catch (e: Exception) {
        throw DeleteDataFailedException.DatabaseException(e.message ?: "")
    }
}
