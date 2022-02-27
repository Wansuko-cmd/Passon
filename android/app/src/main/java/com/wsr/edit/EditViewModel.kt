package com.wsr.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wsr.edit.PasswordEditUiState.Companion.toEditUiState
import com.wsr.edit.PasswordGroupEditUiState.Companion.toEditUiState
import com.wsr.ext.updateWith
import com.wsr.password.getall.GetAllPasswordUseCase
import com.wsr.password.upsert.UpsertPasswordUseCase
import com.wsr.passwordgroup.get.GetPasswordGroupUseCase
import com.wsr.passwordgroup.update.UpdatePasswordGroupUseCase
import com.wsr.state.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditViewModel(
    private val getPasswordGroupUseCase: GetPasswordGroupUseCase,
    private val getAllPasswordUseCase: GetAllPasswordUseCase,
    private val updatePasswordGroupUseCase: UpdatePasswordGroupUseCase,
    private val upsertPasswordUseCase: UpsertPasswordUseCase,
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

            editUiState
                .copyWithTitle(
                    titleState = state.mapBoth(
                        success = { it.title },
                        failure = { ErrorEditUiState(it.message ?: "") },
                    ),
                )
                .copyWithContents(
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
                        passwordGroup = editUiState.contents.passwordGroup.mapBoth(
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
                        passwordGroup = editUiState.contents.passwordGroup.mapBoth(
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

    fun createPassword() {
        viewModelScope.launch {
            val newPasswords = _uiState.value.contents.passwords
                .map { list ->
                    list + PasswordEditUiState.create()
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
        }
    }

    suspend fun save(passwordGroupId: String): State<Unit, ErrorEditUiState> =
        withContext(viewModelScope.coroutineContext) {

            val passwordGroup = savePasswordGroup(passwordGroupId)

            val passwords = savePasswords(passwordGroupId)

            passwordGroup.flatMap {
                passwords.map { }
            }
        }

    private suspend fun savePasswordGroup(passwordGroupId: String) =
        withContext(viewModelScope.coroutineContext) {
            _uiState.value.contents.passwordGroup.flatMap { passwordGroup ->
                updatePasswordGroupUseCase.update(
                    id = passwordGroupId,
                    title = passwordGroup.title,
                    remark = passwordGroup.remark,
                ).mapBoth(
                    success = { },
                    failure = { ErrorEditUiState(it.message ?: "") },
                )
            }
        }

    private suspend fun savePasswords(passwordGroupId: String) =
        withContext(viewModelScope.coroutineContext) {
            _uiState.value.contents.passwords.flatMap { list ->
                list.map {
                    upsertPasswordUseCase.upsert(
                        id = it.id,
                        passwordGroupId = passwordGroupId,
                        name = it.name,
                        password = it.password
                    )
                }
                    .sequence()
                    .mapBoth(
                        success = { },
                        failure = { ErrorEditUiState(it.message ?: "") },
                    )
            }
        }
}
