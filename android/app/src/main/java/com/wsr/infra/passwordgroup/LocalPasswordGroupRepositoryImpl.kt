package com.wsr.infra.passwordgroup

import com.wsr.exceptions.CreateDataFailedException
import com.wsr.exceptions.DeleteDataFailedException
import com.wsr.exceptions.UpdateDataFailedException
import com.wsr.maybe.Maybe
import com.wsr.passwordgroup.PasswordGroup
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passwordgroup.PasswordGroupRepository

class LocalPasswordGroupRepositoryImpl(private val passwordGroupEntityDao: PasswordGroupEntityDao) :
    PasswordGroupRepository {

    override suspend fun create(passwordGroup: PasswordGroup) = try {
        passwordGroupEntityDao
            .insert(passwordGroup.toEntity())
        Maybe.Success(Unit)
    } catch (e: Exception) {
        Maybe.Failure(CreateDataFailedException.SystemError(e.message ?: ""))
    }

    override suspend fun update(id: PasswordGroupId, title: String, remark: String) =
        try {
            passwordGroupEntityDao.getById(id.value)
                .copyWithTitle(title)
                .copyWithRemark(remark)
                .let { passwordGroupEntityDao.update(it) }
            Maybe.Success(Unit)
        } catch (e: Exception) {
            Maybe.Failure(UpdateDataFailedException.SystemError(e.message ?: ""))
        }

    override suspend fun delete(id: PasswordGroupId) = try {
        passwordGroupEntityDao.delete(id.value)
        Maybe.Success(Unit)
    } catch (e: Exception) {
        Maybe.Failure(DeleteDataFailedException.SystemError(e.message ?: ""))
    }
}
