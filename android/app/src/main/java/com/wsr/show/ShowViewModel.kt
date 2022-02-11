package com.wsr.show

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wsr.password.GetAllPasswordUseCase
import com.wsr.state.map
import com.wsr.state.mapBoth
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ShowViewModel(
    private val getAllPasswordUseCase: GetAllPasswordUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ShowUiState("Title"))
    val uiState = _uiState.asStateFlow()

    init {
        getAllPasswordUseCase.data.onEach { state ->
            _uiState.update { showUiState ->
                showUiState.copy(
                    passwordsState = state.mapBoth(
                        success = { list -> list.map { it.toShowUiModel() } },
                        failure = { ErrorShowUiState(it.message ?: "") },
                    ),
                )
            }
        }.launchIn(viewModelScope)
    }

    fun fetchPasswords(passwordGroupId: String) {
        viewModelScope.launch {
            getAllPasswordUseCase.getAllByPasswordGroupId(passwordGroupId)
        }
    }

    fun changePasswordState(id: String, showPassword: Boolean) =
        viewModelScope.launch {
            val newPasswordsState = _uiState.value.passwordsState.map { list ->
                list.map { if (it.id == id) it.copy(showPassword = showPassword) else it }
            }
            _uiState.emit(_uiState.value.copy(passwordsState = newPasswordsState))
        }
}
