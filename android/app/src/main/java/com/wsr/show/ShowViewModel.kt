package com.wsr.show

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wsr.delete.DeletePasswordGroupUseCase
import com.wsr.ext.updateWith
import com.wsr.fetch.FetchPasswordPairUseCase
import com.wsr.show.PasswordGroupShowUiState.Companion.toShowUiModel
import com.wsr.show.PasswordItemShowUiState.Companion.toShowUiModel
import com.wsr.state.map
import com.wsr.state.mapBoth
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ShowViewModel(
    private val fetchPasswordSetUseCase: FetchPasswordPairUseCase,
    private val deletePasswordGroupUseCase: DeletePasswordGroupUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ShowUiState())
    val uiState = _uiState.asStateFlow()

    private val _navigateToIndexEvent = MutableSharedFlow<Unit>()
    val navigateToIndexEvent = _navigateToIndexEvent.asSharedFlow()

    init {
        setup()
    }

    private fun setup() {
        _uiState.updateWith(
            target = fetchPasswordSetUseCase.data,
            coroutineScope = viewModelScope,
        ) { showUiState, state ->
            showUiState.copyWithPasswordGroup(
                passwordGroup = state.map { it.passwordGroup }.mapBoth(
                    success = { it.toShowUiModel() },
                    failure = { ErrorShowUiState(it.message ?: "") },
                )
            ).copyWithPasswordItems(
                passwordItems = state.map { it.passwordItems }.mapBoth(
                    success = { list -> list.map { it.toShowUiModel() } },
                    failure = { ErrorShowUiState(it.message ?: "") }
                )
            )
        }
    }

    fun fetch(passwordGroupId: String) {
        viewModelScope.launch {
            fetchPasswordSetUseCase.fetch(passwordGroupId)
        }
    }

    fun changePasswordState(passwordItemId: String) =
        viewModelScope.launch {

            val newPasswordItemsState = _uiState.value
                .passwordItems
                .map { list ->
                    list.map {
                        if (it.id == passwordItemId) it.copyWithShowPassword(!it.showPassword) else it
                    }
                }

            val newUiState = _uiState.value.copyWithPasswordItems(
                passwordItems = newPasswordItemsState
            )

            _uiState.emit(newUiState)
        }

    fun delete(passwordGroupId: String) {
        viewModelScope.launch {
            deletePasswordGroupUseCase.delete(passwordGroupId)
            _navigateToIndexEvent.emit(Unit)
        }
    }
}
