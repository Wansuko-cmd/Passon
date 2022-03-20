package com.wsr.passworditem.create

import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passworditem.PasswordItemFactory
import com.wsr.passworditem.toUseCaseModel

class CreatePasswordItemUseCaseImpl : CreatePasswordItemUseCase {

    private val passwordFactory = PasswordItemFactory()

    override fun createPasswordInstance(passwordGroupId: String) =
        passwordFactory.create(PasswordGroupId(passwordGroupId)).toUseCaseModel()
}
