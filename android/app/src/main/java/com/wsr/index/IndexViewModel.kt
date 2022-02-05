package com.wsr.index

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wsr.passwordgroup.GetPasswordGroupUseCase
import com.wsr.passwordgroup.TestPasswordGroupRepositoryImpl
import com.wsr.state.mapBoth
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class IndexViewModel : ViewModel() {

    private val getPasswordGroupUseCase = GetPasswordGroupUseCase(TestPasswordGroupRepositoryImpl())

    val uiState = emptyFlow<IndexUiState>()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), IndexUiState())
        .combine(getPasswordGroupUseCase.data) { uiState, state ->
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