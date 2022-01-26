package com.wsr.passwordgroup

import com.wsr.state.State
import com.wsr.user.Email

class GetPasswordGroupUseCase {

    private val passwordGroupRepository: PasswordGroupRepository = TestPasswordGroupRepositoryImpl()

    suspend fun getAllByEmail(email: String): State<List<PasswordGroupUseCaseModel>> =
        com.wsr.state.runCatching {
            passwordGroupRepository
                .getAllByEmail(Email(email))
                .map { it.toUseCaseModel() }
        }

}