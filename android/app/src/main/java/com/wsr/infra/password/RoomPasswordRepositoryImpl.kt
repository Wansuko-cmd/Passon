package com.wsr.infra.password

import com.wsr.infra.password.PasswordEntity.Companion.toEntity
import com.wsr.infra.password.PasswordEntity.Companion.toPassword
import com.wsr.password.Password
import com.wsr.password.PasswordRepository
import com.wsr.utils.UniqueId

class RoomPasswordRepositoryImpl(private val passwordEntityDao: PasswordEntityDao) :
    PasswordRepository {
    override suspend fun getAllByPasswordGroupId(passwordGroupId: UniqueId): List<Password> =
        passwordEntityDao.getAllByPasswordGroupId(passwordGroupId.value).map { it.toPassword() }

    override suspend fun upsert(password: Password) {
        passwordEntityDao.upsert(password.toEntity())
    }

    override suspend fun delete(id: UniqueId) {
        passwordEntityDao.delete(id.value)
    }
}