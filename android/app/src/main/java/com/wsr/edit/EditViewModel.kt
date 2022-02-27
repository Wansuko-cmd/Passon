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
import com.wsr.passwordgroup.upsert.UpdatePasswordGroupUseCase
import com.wsr.state.consume
import com.wsr.state.map
import com.wsr.state.mapBoth
import kotlinx.coroutines.flow.*
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

    private val _event = MutableSharedFlow<EditRefreshEvent>()
    val event = _event.asSharedFlow()

    init {
        setupTitle()
        setupPasswords()
    }

    private fun setupTitle() {
        _uiState.updateWith(
            target = getPasswordGroupUseCase.data,
            coroutineScope = viewModelScope,
        ) { editUiState, state ->

            editUiState.copyWithTitle(
                titleState = state.mapBoth(
                    success = { it.title },
                    failure = { ErrorEditUiState(it.message ?: "") },
                ),
            ).copyWithContents(
                contents = editUiState.contents.copyWithPasswordGroup(
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

            editUiState.copyWithContents(
                contents = editUiState.contents.copyWithPasswords(
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
                editUiState.copyWithContents(
                    contents = editUiState.contents.copyWithPasswordGroup(
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
                editUiState.copyWithContents(
                    contents = editUiState.contents.copyWithPasswordGroup(
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
                editUiState.copyWithContents(
                    contents = editUiState.contents.copyWithPasswords(newPasswords)
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
                    if (it.id == passwordId) it.copyWithPassword(newPassword) else it
                }
            }

        viewModelScope.launch {
            _uiState.update { editUiState ->
                editUiState.copyWithContents(
                    contents = editUiState.contents.copyWithPasswords(newPasswords)
                )
            }
        }
    }

    fun createPassword(passwordGroupId: String) {

        viewModelScope.launch {
            createPasswordUseCase.create(passwordGroupId).consume(
                success = { newPassword ->
                    val newPasswords = _uiState.value.contents.passwords
                        .map { list ->
                            list + newPassword.toEditUiState()
                        }

                    _uiState.update { editUiState ->
                        editUiState.copyWithContents(
                            contents = editUiState.contents.copyWithPasswords(newPasswords)
                        )
                    }

                    newPasswords.consume(
                        success = { _event.emit(EditRefreshEvent(passwords = it)) },
                        failure = {},
                        loading = {},
                    )


                },
                failure = {},
                loading = {},
            )
        }
    }

    suspend fun save(passwordGroupId: String) = viewModelScope.launch {
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
