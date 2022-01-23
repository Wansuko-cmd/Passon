package com.wsr.passwordgroup

import com.wsr.user.Email
import com.wsr.user.TestUserRepositoryImpl
import com.wsr.utils.UniqueId

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

    override fun getAllByEmail(email: Email): List<PasswordGroup> =
        data.filter { it.email == email }

    override fun getById(id: UniqueId): PasswordGroup =
        data.first { it.id == id }

    override fun create(passwordGroup: PasswordGroup) {
        data.add(passwordGroup)
    }

    override fun update(id: UniqueId, title: String, remark: String) {
        val oldPasswordGroup = data.first { it.id == id }
        data.removeIf { it == oldPasswordGroup }
        data.add(PasswordGroup(id, oldPasswordGroup.email, title, remark))
    }

    override fun delete(id: UniqueId) {
        data.removeIf { it.id == id }
    }
}