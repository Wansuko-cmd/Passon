package com.wsr.password.create

import com.wsr.password.PasswordUseCaseModel

interface CreatePasswordUseCase {
    fun createInstance(passwordGroupId: String): PasswordUseCaseModel
}