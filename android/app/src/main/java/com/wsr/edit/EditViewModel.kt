package com.wsr.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wsr.edit.PasswordGroupEditUiState.Companion.toEditUiState
import com.wsr.edit.PasswordItemEditUiState.Companion.toEditUiState
import com.wsr.ext.updateWith
import com.wsr.passwordgroup.get.GetPasswordGroupUseCase
import com.wsr.passwordgroup.update.UpdatePasswordGroupUseCase
import com.wsr.passworditem.create.CreatePasswordItemUseCase
import com.wsr.passworditem.getall.GetAllPasswordItemUseCase
import com.wsr.passworditem.upsert.UpsertPasswordItemUseCase
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
    private val getAllPasswordItemUseCase: GetAllPasswordItemUseCase,
    private val updatePasswordGroupUseCase: UpdatePasswordGroupUseCase,
    private val upsertPasswordItemUseCase: UpsertPasswordItemUseCase,
    private val createPasswordItemUseCase: CreatePasswordItemUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditUiState())
    val uiState = _uiState.asStateFlow()

    private val _editRefreshEvent = MutableSharedFlow<EditRefreshEvent>()
    val editRefreshEvent = _editRefreshEvent.asSharedFlow()

    init {
        setupTitle()
        setupPasswordItems()
    }

    private fun setupTitle() {
        _uiState.updateWith(
            target = getPasswordGroupUseCase.data,
            coroutineScope = viewModelScope,
        ) { editUiState, state ->

            editUiState
                .copyWithPasswordGroup(
                    passwordGroup = state.mapBoth(
                        success = { it.toEditUiState() },
                        failure = { ErrorEditUiState(it.message ?: "") },
                    )
                )
        }
    }

    private fun setupPasswordItems() {
        _uiState.updateWith(
            target = getAllPasswordItemUseCase.data,
            coroutineScope = viewModelScope,
        ) { editUiState, state ->

            editUiState.copyWithPasswordItems(
                passwordItems = state.mapBoth(
                    success = { list -> list.map { it.toEditUiState() } },
                    failure = { ErrorEditUiState(it.message ?: "") }
                )
            )
        }
    }

    fun fetch(passwordGroupId: String) {
        fetchPasswordGroup(passwordGroupId)
        fetchPasswordItems(passwordGroupId)
    }

    private fun fetchPasswordGroup(passwordGroupId: String) {
        viewModelScope.launch {
            getPasswordGroupUseCase.getById(passwordGroupId)
        }
    }

    private fun fetchPasswordItems(passwordGroupId: String) {
        viewModelScope.launch {
            getAllPasswordItemUseCase.getAllByPasswordGroupId(passwordGroupId)
        }
    }

    fun updateTitle(newTitle: String) {
        viewModelScope.launch {
            _uiState.update { editUiState ->
                editUiState.copyWithPasswordGroup(
                    passwordGroup = editUiState.passwordGroup
                        .map { it.copyWithTitle(newTitle) }
                )
            }
        }
    }

    fun updateRemark(newRemark: String) {
        viewModelScope.launch {
            _uiState.update { editUiState ->
                editUiState.copyWithPasswordGroup(
                    passwordGroup = editUiState.passwordGroup
                        .map { it.copyWithRemark(newRemark) }
                )
            }
        }
    }

    fun updateName(passwordItemId: String, newName: String) {
        val newPasswords = _uiState.value
            .passwordItems
            .map { list ->
                list.map {
                    if (it.id == passwordItemId) it.copyWithName(newName) else it
                }
            }

        viewModelScope.launch {
            _uiState.update { editUiState ->
                editUiState.copyWithPasswordItems(newPasswords)
            }
        }
    }

    fun updatePassword(passwordItemId: String, newPassword: String) {
        val newPasswords = _uiState.value
            .passwordItems
            .map { list ->
                list.map {
                    if (it.id == passwordItemId) it.copyWithPassword(newPassword) else it
                }
            }

        viewModelScope.launch {
            _uiState.update { editUiState ->
                editUiState.copyWithPasswordItems(newPasswords)
            }
        }
    }

    fun createPasswordItem(passwordGroupId: String) {
        viewModelScope.launch {
            val newPasswordItem = _uiState.value.passwordItems
                .map { list ->
                    list + createPasswordItemUseCase.createPasswordInstance(passwordGroupId)
                        .toEditUiState()
                }

            _uiState.update { editUiState ->
                editUiState.copyWithPasswordItems(newPasswordItem)
            }

            newPasswordItem.consume(
                success = { _editRefreshEvent.emit(EditRefreshEvent(passwordItems = it)) },
                failure = { /* do nothing */ },
                loading = { /* do nothing */ },
            )
        }
    }

    suspend fun save(passwordGroupId: String): State<Unit, ErrorEditUiState> =
        withContext(viewModelScope.coroutineContext) {
            val passwordGroup = savePasswordGroup(passwordGroupId)

            val passwordItems = savePasswordItems(passwordGroupId)

            when (passwordGroup) {
                is State.Success -> when (passwordItems) {
                    is State.Success -> State.Success(Unit)
                    else -> passwordItems
                }
                else -> passwordGroup
            }
        }

    private suspend fun savePasswordGroup(passwordGroupId: String): State<Unit, ErrorEditUiState> =
        withContext(viewModelScope.coroutineContext) {
            when (val passwordGroup = _uiState.value.passwordGroup) {
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

    private suspend fun savePasswordItems(passwordGroupId: String): State<Unit, ErrorEditUiState> =
        withContext(viewModelScope.coroutineContext) {
            when (val passwords = _uiState.value.passwordItems) {
                is State.Success -> passwords.value.map {
                    upsertPasswordItemUseCase.upsert(
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
