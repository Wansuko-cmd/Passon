package com.wsr.passwordgroup

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.runCatching
import com.wsr.user.Email

class GetPasswordGroupUseCase {

    private val passwordGroupRepository: PasswordGroupRepository = TestPasswordGroupRepositoryImpl()

    suspend fun getAllByEmail(email: String): Result<List<PasswordGroupUseCaseModel>, Throwable> =
        runCatching {
            passwordGroupRepository
                .getAllByEmail(Email(email))
                .map { it.toUseCaseModel() }
        }
}