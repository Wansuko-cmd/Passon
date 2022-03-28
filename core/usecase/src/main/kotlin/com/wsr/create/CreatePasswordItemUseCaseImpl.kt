package com.wsr.create

import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passworditem.PasswordItemFactory
import com.wsr.toUseCaseModel

class CreatePasswordItemUseCaseImpl : CreatePasswordItemUseCase {

    private val passwordFactory = PasswordItemFactory()

    override fun createPasswordItemInstance(passwordGroupId: String) =
        passwordFactory.create(PasswordGroupId(passwordGroupId)).toUseCaseModel()
}
