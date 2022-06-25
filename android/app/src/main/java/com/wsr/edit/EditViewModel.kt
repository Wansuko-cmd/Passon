package com.wsr.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wsr.create.CreatePasswordItemUseCase
import com.wsr.edit.PasswordGroupEditUiState.Companion.toEditUiState
import com.wsr.edit.PasswordItemEditUiState.Companion.toEditUiState
import com.wsr.edit.PasswordItemEditUiState.Companion.toUseCaseModel
import com.wsr.get.GetPasswordPairUseCase
import com.wsr.sync.SyncPasswordPairUseCase
import com.wsr.utils.State
import com.wsr.utils.asState
import com.wsr.utils.consume
import com.wsr.utils.map
import com.wsr.utils.mapBoth
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditViewModel(
    private val getPasswordPairUseCase: GetPasswordPairUseCase,
    private val syncPasswordSetUseCase: SyncPasswordPairUseCase,
    private val createPasswordItemUseCase: CreatePasswordItemUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditUiState())
    val uiState = _uiState.asStateFlow()

    private val _editRefreshEvent = MutableSharedFlow<EditRefreshEvent>()
    val editRefreshEvent = _editRefreshEvent.asSharedFlow()

    fun fetch(passwordGroupId: String) {
        viewModelScope.launch { _uiState.emit(EditUiState()) }
        fetchPasswordPair(passwordGroupId)
    }

    private fun fetchPasswordPair(passwordGroupId: String) {
        viewModelScope.launch {
            val passwordPair = getPasswordPairUseCase.get(passwordGroupId).asState()
            _uiState.update { showUiState ->
                showUiState.copyWithPasswordGroup(
                    passwordGroup = passwordPair.map { it.passwordGroup }.mapBoth(
                        success = { it.toEditUiState() },
                        failure = { ErrorEditUiState(it.message ?: "") },
                    )
                ).copyWithPasswordItems(
                    passwordItems = passwordPair.map { it.passwordItems }.mapBoth(
                        success = { list -> list.map { it.toEditUiState() } },
                        failure = { ErrorEditUiState(it.message ?: "") }
                    )
                )
            }
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

    fun updateShowPassword(passwordItemId: String) =
        viewModelScope.launch {

            val newPasswordItems = _uiState.value
                .passwordItems
                .map { list ->
                    list.map {
                        if (it.id == passwordItemId) it.copyWithShowPassword(!it.showPassword) else it
                    }
                }

            val newUiState = _uiState.value.copyWithPasswordItems(
                passwordItems = newPasswordItems
            )

            _uiState.emit(newUiState)

            newPasswordItems.consume(
                success = { _editRefreshEvent.emit(EditRefreshEvent(passwordItems = it)) },
                failure = { /* do nothing */ },
                loading = { /* do nothing */ },
            )
        }

    fun createPasswordItem(passwordGroupId: String) {
        viewModelScope.launch {
            val newPasswordItems = _uiState.value.passwordItems
                .map { list ->
                    list + createPasswordItemUseCase.createPasswordItemInstance(passwordGroupId)
                        .toEditUiState()
                }

            _uiState.update { editUiState ->
                editUiState.copyWithPasswordItems(newPasswordItems)
            }

            newPasswordItems.consume(
                success = { _editRefreshEvent.emit(EditRefreshEvent(passwordItems = it)) },
                failure = { /* do nothing */ },
                loading = { /* do nothing */ },
            )
        }
    }

    suspend fun sync(passwordGroupId: String): State<Unit, ErrorEditUiState> =
        withContext(viewModelScope.coroutineContext) {
            val passwordGroup = _uiState.value.passwordGroup
            val passwordItems = _uiState.value.passwordItems

            when (passwordGroup) {
                is State.Success -> when (passwordItems) {
                    is State.Success -> {
                        syncPasswordSetUseCase.sync(
                            passwordGroupId,
                            passwordGroup.value.title,
                            passwordGroup.value.remark,
                            passwordItems.value.map { it.toUseCaseModel(passwordGroupId) },
                        )
                        _uiState.update { editUiState -> editUiState.resetEdited() }
                        State.Success(Unit)
                    }
                    is State.Failure -> passwordItems
                    is State.Loading -> passwordItems
                }
                is State.Failure -> passwordGroup
                is State.Loading -> passwordGroup
            }
        }

    fun deletePasswordItem(passwordItemId: String) {
        viewModelScope.launch {
            val newPasswordItems = _uiState.value.passwordItems
                .map { passwordItems -> passwordItems.filter { it.id != passwordItemId } }

            _uiState.update { editUiState ->
                editUiState.copyWithPasswordItems(newPasswordItems)
            }

            newPasswordItems.consume(
                success = { _editRefreshEvent.emit(EditRefreshEvent(passwordItems = it)) },
                failure = { /* do nothing */ },
                loading = { /* do nothing */ },
            )
        }
    }

    fun resetEdited() {
        viewModelScope.launch {
            _uiState.update { editUiState ->
                editUiState.resetEdited()
            }
        }
    }
}
