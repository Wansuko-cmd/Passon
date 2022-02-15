package com.wsr.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wsr.password.GetAllPasswordUseCase
import com.wsr.passwordgroup.get.GetPasswordGroupUseCase
import com.wsr.state.State
import com.wsr.state.mapBoth
import com.wsr.utils.updateWith
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class EditViewModel(
    private val getPasswordGroupUseCase: GetPasswordGroupUseCase,
    private val getAllPasswordUseCase: GetAllPasswordUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditUiState())
    val uiState = _uiState.asStateFlow()

    init {
        setupTitle()
        setupPasswords()
    }

    private fun setupTitle() {
        _uiState.updateWith(
            target = getPasswordGroupUseCase.data,
            coroutineScope = viewModelScope,
        ) { editUiState, state ->
            editUiState.copy(
                titleState = state.mapBoth(
                    success = { it.title },
                    failure = { ErrorEditUiState(it.message ?: "") }
                ),
            )
        }
    }

    private fun setupPasswords() {
        _uiState.updateWith(
            target = getAllPasswordUseCase.data,
            coroutineScope = viewModelScope,
        ) { editUiState, state ->
            editUiState.copy(
                passwordsState = state.mapBoth(
                    success = { list -> list.map { it.toEditUiModel() } },
                    failure = { ErrorEditUiState(it.message ?: "") },
                )
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

    fun updateName(passwordId: String, newName: String) {
        println("passwordId: $passwordId, newName: $newName")
    }

    fun updatePassword(passwordId: String, newPassword: String) {
        println("passwordId: $passwordId, newPassword: $newPassword")
    }
}
