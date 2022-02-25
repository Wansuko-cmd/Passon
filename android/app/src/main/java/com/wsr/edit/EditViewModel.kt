package com.wsr.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wsr.edit.PasswordEditUiState.Companion.toEditUiState
import com.wsr.edit.PasswordEditUiState.Companion.toUseCaseModel
import com.wsr.edit.PasswordGroupEditUiState.Companion.toEditUiState
import com.wsr.ext.updateWith
import com.wsr.password.create.CreatePasswordUseCase
import com.wsr.password.getall.GetAllPasswordUseCase
import com.wsr.password.updateall.UpdateAllPasswordUseCase
import com.wsr.passwordgroup.get.GetPasswordGroupUseCase
import com.wsr.passwordgroup.update.UpdatePasswordGroupUseCase
import com.wsr.state.consume
import com.wsr.state.map
import com.wsr.state.mapBoth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditViewModel(
    private val getPasswordGroupUseCase: GetPasswordGroupUseCase,
    private val getAllPasswordUseCase: GetAllPasswordUseCase,
    private val createPasswordUseCase: CreatePasswordUseCase,
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

            editUiState.copy(
                titleState = state.mapBoth(
                    success = { it.title },
                    failure = { ErrorEditUiState(it.message ?: "") },
                ),
                contents = editUiState.contents.replacePasswordGroup(
                    passwordGroup = state.mapBoth(
                        success = { it.toEditUiState() },
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
                contents = editUiState.contents.replacePasswords(
                    passwords = state.mapBoth(
                        success = { list -> list.map { it.toEditUiState() } },
                        failure = { ErrorEditUiState(it.message ?: "") }
                    )
                )
            )
        }
    }

    fun fetch(passwordGroupId: String) {
        fetchPasswordGroup(passwordGroupId)
        fetchPasswords(passwordGroupId)
    }

    private fun fetchPasswordGroup(passwordGroupId: String) {
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
                    contents = editUiState.contents.replacePasswordGroup(
                        editUiState.contents.passwordGroup.mapBoth(
                            success = { it.replaceTitle(newTitle) },
                            failure = { it },
                        )
                    )
                )
            }
        }
    }

    fun updateRemark(newRemark: String) {
        viewModelScope.launch {
            _uiState.update { editUiState ->
                editUiState.copy(
                    contents = editUiState.contents.replacePasswordGroup(
                        editUiState.contents.passwordGroup.mapBoth(
                            success = { it.replaceRemark(newRemark) },
                            failure = { it }
                        )
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
                list.map {
                    if (it.id == passwordId) it.replaceName(newName) else it
                }
            }

        viewModelScope.launch {
            _uiState.update { editUiState ->
                editUiState.copy(
                    contents = editUiState.contents.replacePasswords(newPasswords)
                )
            }
        }
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
                editUiState.copy(
                    contents = editUiState.contents.replacePasswords(newPasswords)
                )
            }
        }
    }

    suspend fun createPassword(passwordGroupId: String) = viewModelScope.launch(Dispatchers.IO) {
        createPasswordUseCase.create(passwordGroupId).consume(
            success = { newPassword ->
                val newPasswords = _uiState.value.contents.passwords
                    .map { list ->
                        list + newPassword.toEditUiState()
                    }

                _uiState.update { editUiState ->
                    editUiState.copy(
                        contents = editUiState.contents.replacePasswords(newPasswords)
                    )
                }
            },
            failure = {},
            loading = {},
        )
    }

    suspend fun save(passwordGroupId: String) = viewModelScope.launch(Dispatchers.IO) {
        _uiState.value.contents.passwordGroup.consume(
            success = { passwordGroup ->
                updatePasswordGroupUseCase.update(
                    id = passwordGroupId,
                    title = passwordGroup.title,
                    remark = passwordGroup.remark,
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
