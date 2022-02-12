package com.wsr.show

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wsr.exceptions.GetDataFailedException
import com.wsr.password.GetAllPasswordUseCase
import com.wsr.passwordgroup.PasswordGroupUseCaseModel
import com.wsr.passwordgroup.get.GetPasswordGroupUseCase
import com.wsr.state.State
import com.wsr.state.map
import com.wsr.state.mapBoth
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ShowViewModel(
    private val getPasswordGroupUseCase: GetPasswordGroupUseCase,
    private val getAllPasswordUseCase: GetAllPasswordUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ShowUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getPasswordGroupUseCase.data.onEach { state ->
            _uiState.update { showUiState ->
                showUiState.copy(
                    title = state.mapBoth(
                        success = { passwordGroup -> passwordGroup.title },
                        failure = { "Error" }
                    )
                )
            }
        }.launchIn(viewModelScope)

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

    fun fetchTitle(passwordGroupId: String) {
        viewModelScope.launch {
            getPasswordGroupUseCase.getById(passwordGroupId)
        }
    }

    fun fetchPasswords(passwordGroupId: String) {
        viewModelScope.launch {
            getAllPasswordUseCase.getAllByPasswordGroupId(passwordGroupId)
        }
    }

    fun changePasswordState(id: String) =
        viewModelScope.launch {
            val newPasswordsState = _uiState.value.passwordsState.map { list ->
                list.map { if (it.id == id) it.copy(showPassword = !it.showPassword) else it }
            }
            _uiState.emit(_uiState.value.copy(passwordsState = newPasswordsState))
        }
}
