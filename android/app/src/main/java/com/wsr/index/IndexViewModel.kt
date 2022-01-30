package com.wsr.index

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wsr.passwordgroup.GetPasswordGroupUseCase
import com.wsr.state.mapBoth
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class IndexViewModel : ViewModel() {

    private val getPasswordGroupUseCase = GetPasswordGroupUseCase()

    private val _uiState = MutableStateFlow(IndexUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getPasswordGroupUseCase.data.onEach { state ->
            val passwordGroupsState = state.mapBoth(
                success = { list -> list.map { it.toIndexUiState() } },
                failure = { ErrorIndexUiState(it.message ?: "") }
            )

            _uiState.update { it.copy(passwordGroupsState = passwordGroupsState) }

        }.launchIn(viewModelScope)
    }

    fun fetchPasswordGroup(email: String) {
        viewModelScope.launch {
            getPasswordGroupUseCase.getAllByEmail(email)
        }
    }
}