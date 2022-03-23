package com.wsr.passwordgroup.update

import com.wsr.exceptions.UpdateDataFailedException
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passwordgroup.PasswordGroupRepository
import com.wsr.passwordgroup.PasswordGroupUseCaseModel
import com.wsr.passwordgroup.get.GetPasswordGroupQueryService
import com.wsr.passwordgroup.toUseCaseModel
import com.wsr.state.State

class UpdatePasswordGroupUseCaseImpl(
    private val passwordGroupRepository: PasswordGroupRepository,
    private val updatePasswordGroupQueryService: UpdatePasswordGroupQueryService,
) : UpdatePasswordGroupUseCase {

    override suspend fun update(
        id: String,
        title: String,
        remark: String,
    ): State<PasswordGroupUseCaseModel, UpdateDataFailedException> = try {
        passwordGroupRepository.update(PasswordGroupId(id), title, remark)
        updatePasswordGroupQueryService.getById(PasswordGroupId(id))
            .toUseCaseModel()
            .let { State.Success(it) }
    } catch (e: UpdateDataFailedException) {
        State.Failure(e)
    }
}
