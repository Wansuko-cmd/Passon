package com.wsr.password_group

import com.wsr.user.Email

class GetPasswordGroupUseCase {

    private val passwordGroupRepository: PasswordGroupRepository = TestPasswordGroupRepositoryImpl()

    fun getAllByEmail(email: String): List<ExternalPasswordGroup> =
        passwordGroupRepository
            .getAllByEmail(Email(email))
            .map { it.toExternal() }
}