package com.wsr.index

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wsr.passwordgroup.GetPasswordGroupUseCaseImpl
import com.wsr.passwordgroup.TestPasswordGroupRepositoryImpl
import com.wsr.state.mapBoth
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class IndexViewModel : ViewModel() {

    private val getPasswordGroupUseCase = GetPasswordGroupUseCaseImpl(TestPasswordGroupRepositoryImpl())

    val uiState = flowOf(IndexUiState())
        .combine(getPasswordGroupUseCase.data) { uiState, state ->
            Log.d("UI_STATE", "OUT")
            uiState.copy(
                passwordGroupsState = state.mapBoth(
                    success = { list -> list.map { it.toIndexUiState() } },
                    failure = { ErrorIndexUiState(it.message ?: "") }
                ),
            )
        }

    fun fetchPasswordGroup(email: String) {
        viewModelScope.launch {
            getPasswordGroupUseCase.getAllByEmail(email)
        }
    }
}