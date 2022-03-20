package com.wsr.passwordpair.create

import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passwordpair.PasswordPairFactory
import com.wsr.passwordpair.toUseCaseModel

class CreatePasswordPairUseCaseImpl : CreatePasswordPairUseCase {

    private val passwordFactory = PasswordPairFactory()

    override fun createPasswordInstance(passwordGroupId: String) =
        passwordFactory.create(PasswordGroupId(passwordGroupId)).toUseCaseModel()
}
