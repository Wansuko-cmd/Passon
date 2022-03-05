package com.wsr.infra.passwordgroup

import com.wsr.infra.passwordgroup.PasswordGroupEntity.Companion.toEntity
import com.wsr.infra.passwordgroup.PasswordGroupEntity.Companion.toPasswordGroup
import com.wsr.passwordgroup.PasswordGroup
import com.wsr.passwordgroup.PasswordGroupRepository
import com.wsr.user.Email
import com.wsr.utils.UniqueId

class RoomPasswordGroupRepositoryImpl(private val passwordGroupEntityDao: PasswordGroupEntityDao) : PasswordGroupRepository {
    override suspend fun getAllByEmail(email: Email): List<PasswordGroup> =
        passwordGroupEntityDao.getAllByEmail(email.value).map { it.toPasswordGroup() }

    override suspend fun getById(id: UniqueId): PasswordGroup =
        passwordGroupEntityDao.getById(id.value).toPasswordGroup()

    override suspend fun create(passwordGroup: PasswordGroup) {
        passwordGroupEntityDao.insert(passwordGroup.toEntity())
    }

    override suspend fun update(id: UniqueId, title: String?, remark: String?) {
        passwordGroupEntityDao.update(PasswordGroupEntity(id.value, "example1@gmail.com" , title!!, remark!!))
    }

    override suspend fun delete(id: UniqueId) {
        passwordGroupEntityDao.delete(id.value)
    }
}