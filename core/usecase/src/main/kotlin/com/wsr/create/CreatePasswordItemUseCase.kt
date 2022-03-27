package com.wsr.create

import com.wsr.passworditem.PasswordItemUseCaseModel

interface CreatePasswordItemUseCase {
    fun createPasswordInstance(passwordGroupId: String): PasswordItemUseCaseModel
}
