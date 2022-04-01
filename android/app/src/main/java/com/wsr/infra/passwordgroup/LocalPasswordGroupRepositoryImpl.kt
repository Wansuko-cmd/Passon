package com.wsr.infra.passwordgroup

import com.wsr.exceptions.CreateDataFailedException
import com.wsr.exceptions.DeleteDataFailedException
import com.wsr.exceptions.UpdateDataFailedException
import com.wsr.passwordgroup.PasswordGroup
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passwordgroup.PasswordGroupRepository
import com.wsr.state.State

class LocalPasswordGroupRepositoryImpl(private val passwordGroupEntityDao: PasswordGroupEntityDao) :
    PasswordGroupRepository {

    override suspend fun create(passwordGroup: PasswordGroup) = try {
        passwordGroupEntityDao
            .insert(passwordGroup.toEntity())
        State.Success(Unit)
    } catch (e: Exception) {
        State.Failure(CreateDataFailedException.DatabaseException(e.message ?: ""))
    }

    override suspend fun update(id: PasswordGroupId, title: String, remark: String) =
        try {
            passwordGroupEntityDao.getById(id.value)
                .copyWithTitle(title)
                .copyWithRemark(remark)
                .let { passwordGroupEntityDao.update(it) }
            State.Success(Unit)
        } catch (e: Exception) {
            State.Failure(UpdateDataFailedException.DatabaseException(e.message ?: ""))
        }

    override suspend fun delete(id: PasswordGroupId) = try {
        passwordGroupEntityDao.delete(id.value)
        State.Success(Unit)
    } catch (e: Exception) {
        State.Failure(DeleteDataFailedException.DatabaseException(e.message ?: ""))
    }
}
