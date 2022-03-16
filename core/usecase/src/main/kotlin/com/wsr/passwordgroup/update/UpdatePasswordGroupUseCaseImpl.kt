package com.wsr.passwordgroup.update

import com.wsr.exceptions.UpdateDataFailedException
import com.wsr.passwordgroup.PasswordGroupRepository
import com.wsr.passwordgroup.PasswordGroupUseCaseModel
import com.wsr.passwordgroup.toUseCaseModel
import com.wsr.state.State
import com.wsr.utils.UniqueId

class UpdatePasswordGroupUseCaseImpl(
    private val passwordGroupRepository: PasswordGroupRepository,
) : UpdatePasswordGroupUseCase {

    override suspend fun update(
        id: String,
        title: String,
        remark: String,
    ): State<PasswordGroupUseCaseModel, UpdateDataFailedException> = try {
        State.Success(passwordGroupRepository.update(UniqueId.from(id), title, remark).toUseCaseModel())
    } catch (e: UpdateDataFailedException) {
        State.Failure(e)
    }
}
