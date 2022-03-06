package com.wsr.passwordgroup.update

import com.wsr.exceptions.UpdateDataFailedException
import com.wsr.passwordgroup.PasswordGroupRepository
import com.wsr.state.State
import com.wsr.utils.UniqueId

class UpdatePasswordGroupUseCaseImpl(
    private val passwordGroupRepository: PasswordGroupRepository
) : UpdatePasswordGroupUseCase {

    override suspend fun update(
        id: String,
        title: String,
        remark: String,
    ): State<Unit, UpdateDataFailedException> = try {
        passwordGroupRepository.update(UniqueId(id), title, remark)
        State.Success(Unit)
    } catch (e: UpdateDataFailedException) {
        State.Failure(e)
    }
}