package com.wsr.passwordgroup

import com.wsr.user.Email
import com.wsr.user.TestUserRepositoryImpl
import com.wsr.ext.UniqueId

class TestPasswordGroupRepositoryImpl : PasswordGroupRepository {


    companion object {
        internal val data = mutableListOf(
            PasswordGroup(
                UniqueId(),
                TestUserRepositoryImpl.data.first().email,
                "Title1-1",
                "Remark1-1"
            ),
            PasswordGroup(
                UniqueId(),
                TestUserRepositoryImpl.data.first().email,
                "Title1-2",
                "Remark1-2"
            ),
            PasswordGroup(
                UniqueId(),
                TestUserRepositoryImpl.data.first().email,
                "Title1-3",
                "Remark1-3"
            ),
            PasswordGroup(
                UniqueId(),
                TestUserRepositoryImpl.data[1].email,
                "Title2-1",
                "Remark2-1"
            ),
            PasswordGroup(
                UniqueId(),
                TestUserRepositoryImpl.data[2].email,
                "Title3-1",
                "Remark3-1"
            ),
        )
    }

    override suspend fun getAllByEmail(email: Email): List<PasswordGroup> =
        data.filter { it.email == email }

    override suspend fun getById(id: UniqueId): PasswordGroup =
        data.first { it.id == id }

    override suspend fun create(passwordGroup: PasswordGroup) {
        data.add(passwordGroup)
    }

    override suspend fun update(id: UniqueId, title: String?, remark: String?) {
        data.first { it.id == id }.let { oldPasswordGroup ->
            data.removeIf { it == oldPasswordGroup }
            data.add(
                oldPasswordGroup.copy(
                    title = title ?: oldPasswordGroup.title,
                    remark = remark ?: oldPasswordGroup.remark
                )
            )
        }
    }

    override suspend fun delete(id: UniqueId) {
        data.removeIf { it.id == id }
    }
}