package com.wsr.show

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wsr.ext.updateWith
import com.wsr.passwordgroup.get.GetPasswordGroupUseCase
import com.wsr.passworditem.getall.GetAllPasswordItemUseCase
import com.wsr.show.PasswordGroupShowUiState.Companion.toShowUiModel
import com.wsr.show.PasswordItemShowUiState.Companion.toShowUiModel
import com.wsr.state.map
import com.wsr.state.mapBoth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ShowViewModel(
    private val getPasswordGroupUseCase: GetPasswordGroupUseCase,
    private val getAllPasswordItemUseCase: GetAllPasswordItemUseCase,
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
                ),
                contents = showUiState.contents.copyWithPasswordGroup(
                    passwordGroup = state.mapBoth(
                        success = { it.toShowUiModel() },
                        failure = { ErrorShowUiState(it.message ?: "") },
                    )
                )
            )
        }
    }

    private fun setupPasswords() {
        _uiState.updateWith(
            target = getAllPasswordItemUseCase.data,
            coroutineScope = viewModelScope,
        ) { showUiState, state ->
            showUiState.copy(
                contents = showUiState.contents.copyWithPasswordItems(
                    passwords = state.mapBoth(
                        success = { list -> list.map { it.toShowUiModel() } },
                        failure = { ErrorShowUiState(it.message ?: "") }
                    )
                )
            )
        }
    }

    fun fetch(passwordGroupId: String) {
        fetchTitle(passwordGroupId)
        fetchPasswordItems(passwordGroupId)
    }

    private fun fetchTitle(passwordGroupId: String) {
        viewModelScope.launch {
            getPasswordGroupUseCase.getById(passwordGroupId)
        }
    }

    private fun fetchPasswordItems(passwordGroupId: String) {
        viewModelScope.launch {
            getAllPasswordItemUseCase.getAllByPasswordGroupId(passwordGroupId)
        }
    }

    fun changePasswordState(passwordItemId: String) =
        viewModelScope.launch {

            val newPasswordItemsState = _uiState.value
                .contents
                .passwordItems
                .map { list ->
                    list.map {
                        if (it.id == passwordItemId) it.copyWithShowPassword(!it.showPassword) else it
                    }
                }

            val newUiState = _uiState.value.copy(
                contents = _uiState.value.contents.copyWithPasswordItems(
                    passwords = newPasswordItemsState
                )
            )

            _uiState.emit(newUiState)
        }
}
