package com.wsr.password.upsert

import com.wsr.password.Password
import com.wsr.password.PasswordRepository
import com.wsr.password.PasswordUseCaseModel
import com.wsr.password.toUseCaseModel
import com.wsr.state.State
import com.wsr.utils.UniqueId

class UpsertPasswordUseCaseImpl(
    private val passwordRepository: PasswordRepository,
) : UpsertPasswordUseCase {
    override suspend fun upsert(
        id: String,
        passwordGroupId: String,
        name: String,
        password: String
    ): State<PasswordUseCaseModel, Throwable> = try {

        Password(
            id = UniqueId(id),
            passwordGroupId = UniqueId(passwordGroupId),
            name = name,
            password = password
        )
            .also { passwordRepository.upsert(it) }
            .let { State.Success(it.toUseCaseModel()) }

    } catch (e: Exception) {
        State.Failure(e)
    }
}