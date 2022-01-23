package com.wsr.index

import androidx.lifecycle.ViewModel
import com.wsr.passwordgroup.ExternalPasswordGroup
import com.wsr.passwordgroup.GetPasswordGroupUseCase

class IndexViewModel : ViewModel() {

    private val getPasswordGroupUseCase = GetPasswordGroupUseCase()

    fun getAllPasswordGroup(email: String): List<ExternalPasswordGroup> =
        getPasswordGroupUseCase.getAllByEmail(email)
}