package com.wsr.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wsr.edit.PasswordGroupEditUiState.Companion.toEditUiState
import com.wsr.edit.PasswordPairEditUiState.Companion.toEditUiState
import com.wsr.ext.updateWith
import com.wsr.passwordgroup.get.GetPasswordGroupUseCase
import com.wsr.passwordgroup.update.UpdatePasswordGroupUseCase
import com.wsr.passwordpair.create.CreatePasswordPairUseCase
import com.wsr.passwordpair.getall.GetAllPasswordPairUseCase
import com.wsr.passwordpair.upsert.UpsertPasswordPairUseCase
import com.wsr.state.State
import com.wsr.state.consume
import com.wsr.state.map
import com.wsr.state.mapBoth
import com.wsr.state.sequence
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditViewModel(
    private val getPasswordGroupUseCase: GetPasswordGroupUseCase,
    private val getAllPasswordPairUseCase: GetAllPasswordPairUseCase,
    private val updatePasswordGroupUseCase: UpdatePasswordGroupUseCase,
    private val upsertPasswordPairUseCase: UpsertPasswordPairUseCase,
    private val createPasswordPairUseCase: CreatePasswordPairUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditUiState())
    val uiState = _uiState.asStateFlow()

    private val _editRefreshEvent = MutableSharedFlow<EditRefreshEvent>()
    val editRefreshEvent = _editRefreshEvent.asSharedFlow()

    init {
        setupTitle()
        setupPasswordPairs()
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

    private fun setupPasswordPairs() {
        _uiState.updateWith(
            target = getAllPasswordPairUseCase.data,
            coroutineScope = viewModelScope,
        ) { editUiState, state ->

            editUiState.copyWithContents(
                contents = editUiState.contents.copyWithPasswordPairs(
                    passwordPairs = state.mapBoth(
                        success = { list -> list.map { it.toEditUiState() } },
                        failure = { ErrorEditUiState(it.message ?: "") }
                    )
                )
            )
        }
    }

    fun fetch(passwordGroupId: String) {
        fetchPasswordGroup(passwordGroupId)
        fetchPasswordPairs(passwordGroupId)
    }

    private fun fetchPasswordGroup(passwordGroupId: String) {
        viewModelScope.launch {
            getPasswordGroupUseCase.getById(passwordGroupId)
        }
    }

    private fun fetchPasswordPairs(passwordGroupId: String) {
        viewModelScope.launch {
            getAllPasswordPairUseCase.getAllByPasswordGroupId(passwordGroupId)
        }
    }

    fun updateTitle(newTitle: String) {
        viewModelScope.launch {
            _uiState.update { editUiState ->
                editUiState.copyWithContents(
                    contents = editUiState.contents.copyWithPasswordGroup(
                        passwordGroup = editUiState.contents.passwordGroup
                            .map { it.copyWithTitle(newTitle) }
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
                        passwordGroup = editUiState.contents.passwordGroup
                            .map { it.copyWithRemark(newRemark) }
                    )
                )
            }
        }
    }

    fun updateName(passwordId: String, newName: String) {
        val newPasswords = _uiState.value
            .contents
            .passwordPairs
            .map { list ->
                list.map {
                    if (it.id == passwordId) it.copyWithName(newName) else it
                }
            }

        viewModelScope.launch {
            _uiState.update { editUiState ->
                editUiState.copyWithContents(
                    contents = editUiState.contents.copyWithPasswordPairs(newPasswords)
                )
            }
        }
    }

    fun updatePassword(passwordId: String, newPassword: String) {
        val newPasswords = _uiState.value
            .contents
            .passwordPairs
            .map { list ->
                list.map {
                    if (it.id == passwordId) it.copyWithPassword(newPassword) else it
                }
            }

        viewModelScope.launch {
            _uiState.update { editUiState ->
                editUiState.copyWithContents(
                    contents = editUiState.contents.copyWithPasswordPairs(newPasswords)
                )
            }
        }
    }

    fun createPasswordPair(passwordGroupId: String) {
        viewModelScope.launch {
            val newPasswordPair = _uiState.value.contents.passwordPairs
                .map { list ->
                    list + createPasswordPairUseCase.createPasswordInstance(passwordGroupId)
                        .toEditUiState()
                }

            _uiState.update { editUiState ->
                editUiState.copyWithContents(
                    contents = editUiState.contents.copyWithPasswordPairs(newPasswordPair)
                )
            }

            newPasswordPair.consume(
                success = { _editRefreshEvent.emit(EditRefreshEvent(passwordPairs = it)) },
                failure = { /* do nothing */ },
                loading = { /* do nothing */ },
            )
        }
    }

    suspend fun save(passwordGroupId: String): State<Unit, ErrorEditUiState> =
        withContext(viewModelScope.coroutineContext) {
            val passwordGroup = savePasswordGroup(passwordGroupId)

            val passwordPairs = savePasswordPairs(passwordGroupId)

            when (passwordGroup) {
                is State.Success -> when (passwordPairs) {
                    is State.Success -> State.Success(Unit)
                    else -> passwordPairs
                }
                else -> passwordGroup
            }
        }

    private suspend fun savePasswordGroup(passwordGroupId: String): State<Unit, ErrorEditUiState> =
        withContext(viewModelScope.coroutineContext) {
            when (val passwordGroup = _uiState.value.contents.passwordGroup) {
                is State.Success -> updatePasswordGroupUseCase.update(
                    id = passwordGroupId,
                    title = passwordGroup.value.title,
                    remark = passwordGroup.value.remark,
                ).mapBoth(
                    success = { /* do nothing */ },
                    failure = { ErrorEditUiState(it.message ?: "") },
                )
                is State.Failure -> passwordGroup
                is State.Loading -> passwordGroup
            }
        }

    private suspend fun savePasswordPairs(passwordGroupId: String): State<Unit, ErrorEditUiState> =
        withContext(viewModelScope.coroutineContext) {
            when (val passwords = _uiState.value.contents.passwordPairs) {
                is State.Success -> passwords.value.map {
                    upsertPasswordPairUseCase.upsert(
                        id = it.id,
                        passwordGroupId = passwordGroupId,
                        name = it.name,
                        password = it.password
                    )
                }
                    .sequence()
                    .mapBoth(
                        success = { /* do nothing */ },
                        failure = { ErrorEditUiState(it.message ?: "") },
                    )
                is State.Failure -> passwords
                is State.Loading -> passwords
            }
        }
}
