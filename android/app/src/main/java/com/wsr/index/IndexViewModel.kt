package com.wsr.index

import androidx.lifecycle.ViewModel
import com.wsr.passwordgroup.GetPasswordGroupUseCase

class IndexViewModel : ViewModel() {

    private val getPasswordGroupUseCase = GetPasswordGroupUseCase()

    fun getAllPasswordGroup(email: String): List<PasswordGroupIndexUIModel> =
        getPasswordGroupUseCase.getAllByEmail(email).map { it.toIndexUIModel() }
}