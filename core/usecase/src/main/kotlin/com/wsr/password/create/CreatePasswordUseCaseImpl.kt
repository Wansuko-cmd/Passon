package com.wsr.password.create

import com.wsr.password.PasswordFactory
import com.wsr.password.toUseCaseModel
import com.wsr.passwordgroup.PasswordGroupId

class CreatePasswordUseCaseImpl : CreatePasswordUseCase {

    private val passwordFactory = PasswordFactory()

    override fun createPasswordInstance(passwordGroupId: String) =
        passwordFactory.create(PasswordGroupId(passwordGroupId)).toUseCaseModel()
}
