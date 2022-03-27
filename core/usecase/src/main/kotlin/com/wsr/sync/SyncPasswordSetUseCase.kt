package com.wsr.sync

import com.wsr.passwordgroup.PasswordGroupUseCaseModel
import com.wsr.passworditem.PasswordItemUseCaseModel

interface SyncPasswordSetUseCase {
    fun update(passwordGroup: PasswordGroupUseCaseModel, passwordItems: List<PasswordItemUseCaseModel>)
}
