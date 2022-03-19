package com.wsr.password

import com.wsr.passwordgroup.TestPasswordGroupRepositoryImpl
import com.wsr.utils.UniqueId

class TestPasswordRepositoryImpl : PasswordRepository {

    companion object {
        internal val data = mutableListOf(
            Password.of(
                passwordGroupId = TestPasswordGroupRepositoryImpl.data.first().id,
                name = "Name1-1",
                password = "Password1-1",
            ),
            Password.of(
                passwordGroupId = TestPasswordGroupRepositoryImpl.data.first().id,
                name = "Name1-2",
                password = "Password1-2",
            ),
            Password.of(
                passwordGroupId = TestPasswordGroupRepositoryImpl.data[1].id,
                name = "Name2-1",
                password = "Password2-1",
            ),
            Password.of(
                passwordGroupId = TestPasswordGroupRepositoryImpl.data[2].id,
                name = "Name3-1",
                password = "Password3-1",
            ),
        )
    }

    override suspend fun getAllByPasswordGroupId(passwordGroupId: UniqueId): List<Password> =
        data.filter { it.passwordGroupId == passwordGroupId }

    override suspend fun upsert(password: Password): Password {
        val index = data.indexOfFirst { it.id == password.id }
        if (index == -1) data.add(password)
        else data[index] = password

        return data[index]
    }

    override suspend fun delete(id: UniqueId) {
        TODO("Not yet implemented")
    }
}
