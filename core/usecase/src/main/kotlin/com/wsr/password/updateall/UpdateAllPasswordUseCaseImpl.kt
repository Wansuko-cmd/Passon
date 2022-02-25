package com.wsr.password.updateall

import com.wsr.exceptions.UpdateDataFailedException
import com.wsr.password.PasswordRepository
import com.wsr.password.PasswordUseCaseModel
import com.wsr.password.toPassword
import com.wsr.state.State

class UpdateAllPasswordUseCaseImpl(
    private val passwordRepository: PasswordRepository
) : UpdateAllPasswordUseCase {

    override suspend fun updateAll(
        passwords: List<PasswordUseCaseModel>
    ): State<Boolean, UpdateDataFailedException> = try {
        passwords.forEach { passwordRepository.update(it.toPassword()) }

        State.Success(true)
    } catch (e: UpdateDataFailedException) {
        State.Failure(e)
    }
}
