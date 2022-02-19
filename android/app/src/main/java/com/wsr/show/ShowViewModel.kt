package com.wsr.show

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wsr.password.getall.GetAllPasswordUseCase
import com.wsr.passwordgroup.get.GetPasswordGroupUseCase
import com.wsr.state.map
import com.wsr.state.mapBoth
import com.wsr.ext.updateWith
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ShowViewModel(
    private val getPasswordGroupUseCase: GetPasswordGroupUseCase,
    private val getAllPasswordUseCase: GetAllPasswordUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ShowUiState())
    val uiState = _uiState.asStateFlow()

    init {
        setupTitle()
        setupPasswords()
    }

    private fun setupTitle() {
        _uiState.updateWith(
            target = getPasswordGroupUseCase.data,
            coroutineScope = viewModelScope,
        ) { showUiState, state ->
            showUiState.copy(
                titleState = state.mapBoth(
                    success = { passwordGroup -> passwordGroup.title },
                    failure = { ErrorShowUiState(it.message ?: "") }
                )
            )
        }
    }

    private fun setupPasswords() {
        _uiState.updateWith(
            target = getAllPasswordUseCase.data,
            coroutineScope = viewModelScope,
        ) { showUiState, state ->
            showUiState.copy(
                passwordsState = state.mapBoth(
                    success = { list -> list.map { it.toShowUiModel() } },
                    failure = { ErrorShowUiState(it.message ?: "") },
                ),
            )
        }
    }


    fun fetch(passwordGroupId: String) {
        fetchTitle(passwordGroupId)
        fetchPasswords(passwordGroupId)
    }

    private fun fetchTitle(passwordGroupId: String) {
        viewModelScope.launch {
            getPasswordGroupUseCase.getById(passwordGroupId)
        }
    }

    private fun fetchPasswords(passwordGroupId: String) {
        viewModelScope.launch {
            getAllPasswordUseCase.getAllByPasswordGroupId(passwordGroupId)
        }
    }


    fun changePasswordState(passwordId: String) =
        viewModelScope.launch {
            val newPasswordsState = _uiState.value.passwordsState.map { list ->
                list.map { if (it.id == passwordId) it.copy(showPassword = !it.showPassword) else it }
            }
            _uiState.emit(_uiState.value.copy(passwordsState = newPasswordsState))
        }
}
