package com.wsr.passwordpair.upsert

import com.wsr.exceptions.UpsertDataFailedException
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passwordpair.Name
import com.wsr.passwordpair.Password
import com.wsr.passwordpair.PasswordPairFactory
import com.wsr.passwordpair.PasswordPairId
import com.wsr.passwordpair.PasswordPairRepository
import com.wsr.passwordpair.PasswordPairUseCaseModel
import com.wsr.passwordpair.toUseCaseModel
import com.wsr.state.State

class UpsertPasswordPairUseCaseImpl(
    private val passwordRepository: PasswordPairRepository,
) : UpsertPasswordPairUseCase {

    private val passwordFactory = PasswordPairFactory()

    override suspend fun upsert(
        id: String,
        passwordGroupId: String,
        name: String,
        password: String,
    ): State<PasswordPairUseCaseModel, UpsertDataFailedException> = try {
        val newPassword = passwordFactory.create(
            passwordPairId = PasswordPairId(id),
            passwordGroupId = PasswordGroupId(passwordGroupId),
            name = Name(name),
            password = Password(password),
        )
        State.Success(passwordRepository.upsert(newPassword).toUseCaseModel())
    } catch (e: UpsertDataFailedException) {
        State.Failure(e)
    }
}
