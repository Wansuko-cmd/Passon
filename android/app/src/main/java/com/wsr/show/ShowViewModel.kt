package com.wsr.show

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wsr.delete.DeletePasswordGroupUseCase
import com.wsr.get.GetPasswordPairUseCase
import com.wsr.show.PasswordGroupShowUiState.Companion.toShowUiModel
import com.wsr.show.PasswordItemShowUiState.Companion.toShowUiModel
import com.wsr.utils.asState
import com.wsr.utils.map
import com.wsr.utils.mapBoth
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ShowViewModel(
    private val getPasswordPairUseCase: GetPasswordPairUseCase,
    private val deletePasswordGroupUseCase: DeletePasswordGroupUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ShowUiState())
    val uiState = _uiState.asStateFlow()

    private val _navigateToIndexEvent = MutableSharedFlow<Unit>()
    val navigateToIndexEvent = _navigateToIndexEvent.asSharedFlow()

    fun fetch(passwordGroupId: String) {
        viewModelScope.launch { _uiState.emit(ShowUiState()) }
        fetchPasswordPair(passwordGroupId)
    }

    private fun fetchPasswordPair(passwordGroupId: String) {
        viewModelScope.launch {
            val passwordPair = getPasswordPairUseCase.get(passwordGroupId).asState()
            _uiState.update { showUiState ->
                showUiState.copyWithPasswordGroup(
                    passwordGroup = passwordPair.map { it.passwordGroup }.mapBoth(
                        success = { it.toShowUiModel() },
                        failure = { ErrorShowUiState(it.message ?: "") },
                    )
                ).copyWithPasswordItems(
                    passwordItems = passwordPair.map { it.passwordItems }.mapBoth(
                        success = { list -> list.map { it.toShowUiModel() } },
                        failure = { ErrorShowUiState(it.message ?: "") }
                    )
                )
            }
        }
    }

    fun updateShowPassword(passwordItemId: String) =
        viewModelScope.launch {
            _uiState.update { showUiState ->
                val passwordItems = showUiState.passwordItems.map { list ->
                    list.map {
                        if (it.id == passwordItemId) it.copyWithShowPassword(!it.showPassword) else it
                    }
                }
                showUiState.copyWithPasswordItems(
                    passwordItems = passwordItems
                )
            }
        }

    fun delete(passwordGroupId: String) {
        viewModelScope.launch {
            deletePasswordGroupUseCase.delete(passwordGroupId)
            _navigateToIndexEvent.emit(Unit)
        }
    }
}
