package com.wsr.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wsr.edit.PasswordEditUiState.Companion.toEditUiState
import com.wsr.edit.PasswordEditUiState.Companion.toUseCaseModel
import com.wsr.ext.updateWith
import com.wsr.password.getall.GetAllPasswordUseCase
import com.wsr.password.updateall.UpdateAllPasswordUseCase
import com.wsr.passwordgroup.get.GetPasswordGroupUseCase
import com.wsr.passwordgroup.update.UpdatePasswordGroupUseCase
import com.wsr.state.State
import com.wsr.state.consume
import com.wsr.state.map
import com.wsr.state.mapBoth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditViewModel(
    private val getPasswordGroupUseCase: GetPasswordGroupUseCase,
    private val getAllPasswordUseCase: GetAllPasswordUseCase,
    private val updatePasswordGroupUseCase: UpdatePasswordGroupUseCase,
    private val updateAllPasswordUseCase: UpdateAllPasswordUseCase,
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

            editUiState.apply {
                titleState.map {
                    state.mapBoth(
                        success = { it.title },
                        failure = { ErrorEditUiState(it.message ?: "") },
                    )
                }

                contents.replaceTitle(
                    title = state.mapBoth(
                        success = { it.title },
                        failure = { ErrorEditUiState(it.message ?: "") },
                    )
                )
            }
        }
    }

    private fun setupPasswords() {
        _uiState.updateWith(
            target = getAllPasswordUseCase.data,
            coroutineScope = viewModelScope,
        ) { editUiState, state ->

            editUiState.apply {
                contents.replacePasswords(
                    passwords = state.mapBoth(
                        success = { list -> list.map { it.toEditUiState() } },
                        failure = { ErrorEditUiState(it.message ?: "") }
                    )
                )
            }
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
                editUiState.apply {
                    contents.replaceTitle(State.Success(newTitle))
                }
            }
        }
    }

    fun updateName(passwordId: String, newName: String) {
        val newPasswords = _uiState.value
            .contents
            .passwords
            .map { list ->
                list.map {
                    if (it.id == passwordId) it.replaceName(newName) else it
                }
            }

        viewModelScope.launch {
            _uiState.update { editUiState ->
                editUiState.apply {
                    contents.replacePasswords(newPasswords)
                }
            }
        }
        println("passwordId: $passwordId, newName: $newName")
    }

    fun updatePassword(passwordId: String, newPassword: String) {
        val newPasswords = _uiState.value
            .contents
            .passwords
            .map { list ->
                list.map {
                    if (it.id == passwordId) it.replacePassword(newPassword) else it
                }
            }

        viewModelScope.launch {
            _uiState.update { editUiState ->
                editUiState.apply {
                    contents.replacePasswords(newPasswords)
                }
            }
        }
        println("passwordId: $passwordId, newPassword: $newPassword")
    }

    fun save(passwordGroupId: String) {
        viewModelScope.launch {
            _uiState.value.contents.title.consume(
                success = { title ->
                    updatePasswordGroupUseCase.update(
                        id = passwordGroupId,
                        title = title,
                    )
                },
                failure = {},
                loading = {},
            )
            _uiState.value.contents.passwords.consume(
                success = { list ->
                    updateAllPasswordUseCase.updateAll(list.map { it.toUseCaseModel(passwordGroupId) })
                },
                failure = {},
                loading = {},
            )
        }
    }
}
