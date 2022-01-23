package com.wsr.passwordgroup

import com.wsr.user.Email

class GetPasswordGroupUseCase {

    private val passwordGroupRepository: PasswordGroupRepository = TestPasswordGroupRepositoryImpl()

    fun getAllByEmail(email: String): List<PasswordGroupUseCaseModel> =
        passwordGroupRepository
            .getAllByEmail(Email(email))
            .map { it.toUseCaseModel() }
}