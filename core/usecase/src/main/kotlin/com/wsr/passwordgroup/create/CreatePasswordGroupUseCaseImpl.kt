package com.wsr.passwordgroup.create

import com.wsr.exceptions.CreateDataFailedException
import com.wsr.passwordgroup.PasswordGroup
import com.wsr.passwordgroup.PasswordGroupRepository
import com.wsr.passwordgroup.PasswordGroupUseCaseModel
import com.wsr.passwordgroup.toUseCaseModel
import com.wsr.state.State
import com.wsr.user.Email
import com.wsr.utils.UniqueId
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CreatePasswordGroupUseCaseImpl(
    private val passwordGroupRepository: PasswordGroupRepository
) : CreatePasswordGroupUseCase {

    private val _data =
        MutableStateFlow<State<PasswordGroupUseCaseModel, CreateDataFailedException>>(State.Loading)
    override val data get() = _data.asStateFlow()

    override suspend fun create(email: String, title: String) {
        try {
            val passwordGroup = PasswordGroup(
                id = UniqueId(),
                email = Email(email),
                title = title,
                remark = "",
            )

            passwordGroupRepository.create(passwordGroup)

            _data.emit(State.Success(passwordGroup.toUseCaseModel()))

        } catch (e: CreateDataFailedException) {
            _data.emit(State.Failure(e))
        }
    }
}