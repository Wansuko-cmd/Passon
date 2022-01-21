package com.wsr.view_model.index

import androidx.lifecycle.ViewModel
import com.wsr.password_group.ExternalPasswordGroup
import com.wsr.password_group.GetPasswordGroupUseCase

class IndexViewModel : ViewModel() {

    private val getPasswordGroupUseCase = GetPasswordGroupUseCase()

    fun getAllPasswordGroup(email: String): List<ExternalPasswordGroup> =
        getPasswordGroupUseCase.getAllByEmail(email)
}