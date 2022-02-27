package com.wsr.password

import com.wsr.passwordgroup.TestPasswordGroupRepositoryImpl
import com.wsr.utils.UniqueId

class TestPasswordRepositoryImpl : PasswordRepository {

    companion object {
        internal val data = mutableListOf(
            Password(
                UniqueId(),
                TestPasswordGroupRepositoryImpl.data.first().id,
                "Name1-1",
                "Password1-1",
            ),
            Password(
                UniqueId(),
                TestPasswordGroupRepositoryImpl.data.first().id,
                "Name1-2",
                "Password1-2",
            ),
            Password(
                UniqueId(),
                TestPasswordGroupRepositoryImpl.data[1].id,
                "Name2-1",
                "Password2-1",
            ),
            Password(
                UniqueId(),
                TestPasswordGroupRepositoryImpl.data[2].id,
                "Name3-1",
                "Password3-1",
            ),
        )
    }


    override suspend fun getAllByPasswordGroupId(passwordGroupId: UniqueId): List<Password> =
        data.filter { it.passwordGroupId == passwordGroupId }


    override suspend fun upsert(password: Password) {
        data.indexOfFirst { it.id == password.id }.let { index ->
            if (index == -1) data.add(password)
            else data[index] = password
        }
    }


    override suspend fun create(password: Password) {
        data.add(password)
    }

    override suspend fun update(password: Password) {
        val index = data.indexOfFirst { it.id == password.id }
        data[index] = password
    }

    override suspend fun delete(id: UniqueId) {
        TODO("Not yet implemented")
    }
}