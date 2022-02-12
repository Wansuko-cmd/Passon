package com.wsr.index

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wsr.passwordgroup.GetAllPasswordGroupUseCase
import com.wsr.state.mapBoth
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class IndexViewModel(
    private val getAllPasswordGroupUseCase: GetAllPasswordGroupUseCase,
) : ViewModel() {

    val uiState = flowOf(IndexUiState())
        .combine(getAllPasswordGroupUseCase.data) { uiState, state ->
            uiState.copy(
                passwordGroupsState = state.mapBoth(
                    success = { list -> list.map { it.toIndexUiState() } },
                    failure = { ErrorIndexUiState(it.message ?: "") },
                ),
            )
        }

    fun fetchPasswordGroups(email: String) {
        viewModelScope.launch {
            getAllPasswordGroupUseCase.getAllByEmail(email)
        }
    }
}