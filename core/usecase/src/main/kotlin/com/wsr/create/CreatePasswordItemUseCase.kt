package com.wsr.create

import com.wsr.PasswordItemUseCaseModel

interface CreatePasswordItemUseCase {
    fun createPasswordItemInstance(passwordGroupId: String): PasswordItemUseCaseModel
}
