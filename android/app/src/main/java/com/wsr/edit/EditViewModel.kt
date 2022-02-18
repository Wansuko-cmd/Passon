package com.wsr.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wsr.password.GetAllPasswordUseCase
import com.wsr.passwordgroup.get.GetPasswordGroupUseCase
import com.wsr.state.State
import com.wsr.state.map
import com.wsr.state.mapBoth
import com.wsr.utils.updateWith
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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
                    failure = { ErrorEditUiState(it.message ?: "") },
                ),
                contents = editUiState.contents.copy(
                    title = state.mapBoth(
                        success = { it.title },
                        failure = { ErrorEditUiState(it.message ?: "") },
                    )
                )
            )
        }
    }

    private fun setupPasswords() {
        _uiState.updateWith(
            target = getAllPasswordUseCase.data,
            coroutineScope = viewModelScope,
        ) { editUiState, state ->

            editUiState.copy(
                contents = editUiState.contents.copy(
                    passwords = state.mapBoth(
                        success = { list -> list.map { it.toEditUiState() } },
                        failure = { ErrorEditUiState(it.message ?: "") }
                    )
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

    fun updateTitle(newTitle: String) {
        viewModelScope.launch {
            _uiState.update { editUiState ->
                editUiState.copy(
                    contents = editUiState.contents.copy(
                        title = State.Success(newTitle)
                    )
                )
            }
        }
    }

    fun updateName(passwordId: String, newName: String) {
        val newPasswords = _uiState.value
            .contents
            .passwords
            .map { list ->
                list.map { if (it.id == passwordId) it.copy(name = newName) else it }
            }

        viewModelScope.launch {
            _uiState.update { editUiState ->
                editUiState.copy(
                    contents = editUiState.contents.copy(
                        passwords = newPasswords
                    )
                )
            }
        }
        println("passwordId: $passwordId, newName: $newName")
    }

    fun updatePassword(passwordId: String, newPassword: String) {
        val newPasswords = _uiState.value
            .contents
            .passwords
            .map { list ->
                list.map { if (it.id == passwordId) it.copy(password = newPassword) else it }
            }

        viewModelScope.launch {
            _uiState.update { editUiState ->
                editUiState.copy(
                    contents = editUiState.contents.copy(
                        passwords = newPasswords
                    )
                )
            }
        }
        println("passwordId: $passwordId, newPassword: $newPassword")
    }
}
