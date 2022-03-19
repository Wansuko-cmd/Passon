package com.wsr.passwordgroup

import com.wsr.email.Email
import com.wsr.exceptions.GetAllDataFailedException
import com.wsr.exceptions.GetDataFailedException
import com.wsr.exceptions.UpdateDataFailedException
import com.wsr.utils.UniqueId

class TestPasswordGroupRepositoryImpl : PasswordGroupRepository {

    companion object {
        internal val data = mutableListOf(
            PasswordGroup.of(
                email = Email.from("email@example.com"),
                title = "Title1-1",
                remark = "Remark1-1"
            ),
            PasswordGroup.of(
                email = Email.from("email@example.com"),
                title = "Title1-2",
                remark = "Remark1-2"
            ),
            PasswordGroup.of(
                email = Email.from("email@example.com"),
                title = "Title1-3",
                remark = "Remark1-3"
            ),
            PasswordGroup.of(
                email = Email.from("email@example.com"),
                title = "Title2-1",
                remark = "Remark2-1"
            ),
            PasswordGroup.of(
                email = Email.from("email@example.com"),
                title = "Title3-1",
                remark = "Remark3-1"
            ),
        )
    }

    @Throws(GetAllDataFailedException::class)
    override suspend fun getAllByEmail(email: Email): List<PasswordGroup> =
        data.filter { it.email == email }

    @Throws(GetDataFailedException::class)
    override suspend fun getById(id: UniqueId): PasswordGroup =
        data.first { it.id == id }

    override suspend fun create(passwordGroup: PasswordGroup): PasswordGroup {
        data.add(passwordGroup)
        return passwordGroup
    }

    @Throws(UpdateDataFailedException::class)
    override suspend fun update(id: UniqueId, title: String, remark: String): PasswordGroup {
        data.first { it.id == id }.let { oldPasswordGroup ->
            data.removeIf { it == oldPasswordGroup }
            val newPasswordGroup = oldPasswordGroup.copy(
                title = title,
                remark = remark,
            )
            data.add(newPasswordGroup)
            return newPasswordGroup
        }
    }

    override suspend fun delete(id: UniqueId) {
        data.removeIf { it.id == id }
    }
}
