package com.wsr.update

import com.wsr.passwordgroup.PasswordGroupUseCaseModel
import com.wsr.passworditem.PasswordItemUseCaseModel

interface UpdateCommandUseCase {
    fun update(passwordGroup: PasswordGroupUseCaseModel, passwordItems: List<PasswordItemUseCaseModel>)
}
