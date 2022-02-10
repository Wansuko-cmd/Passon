package com.wsr.show

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wsr.password.GetAllPasswordUseCase
import com.wsr.state.mapBoth
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class ShowViewModel(
    private val getAllPasswordUseCase: GetAllPasswordUseCase
) : ViewModel() {

    val uiState = flowOf(ShowUiState("Title"))
        .combine(getAllPasswordUseCase.data) { uiState, state ->
            uiState.copy(
                passwordsState = state.mapBoth(
                    success = { list -> list.map { it.toShowUiModel() } },
                    failure = { ErrorShowUiState(it.message ?: "") },
                ),
            )
        }

    fun fetchPasswords(passwordGroupId: String) {
        viewModelScope.launch {
            getAllPasswordUseCase.getAllByPasswordGroupId(passwordGroupId)
        }
    }
}