package com.wsr.view_model.index

import androidx.lifecycle.ViewModel
import com.wsr.password_group.ExternalPasswordGroup
import com.wsr.password_group.GetPasswordGroupUseCase
import com.wsr.password_group.TestPasswordGroupRepositoryImpl

class IndexViewModel : ViewModel() {

    private val getPasswordGroupUseCase = GetPasswordGroupUseCase(TestPasswordGroupRepositoryImpl())

    fun getAllPasswordGroup(email: String): List<ExternalPasswordGroup> =
        getPasswordGroupUseCase.getAllByEmail(email)
}