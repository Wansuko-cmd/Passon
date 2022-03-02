package com.wsr.password.create

import com.wsr.password.Password
import com.wsr.password.toUseCaseModel
import com.wsr.utils.UniqueId

class CreatePasswordUseCaseImpl : CreatePasswordUseCase {
    override fun createInstance(passwordGroupId: String) =
        Password(passwordGroupId = UniqueId(passwordGroupId)).toUseCaseModel()
}