package com.wsr.show

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wsr.ext.updateWith
import com.wsr.passwordgroup.get.GetPasswordGroupUseCase
import com.wsr.passwordpair.getall.GetAllPasswordPairUseCase
import com.wsr.show.PasswordGroupShowUiState.Companion.toShowUiModel
import com.wsr.show.PasswordPairShowUiState.Companion.toShowUiModel
import com.wsr.state.map
import com.wsr.state.mapBoth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ShowViewModel(
    private val getPasswordGroupUseCase: GetPasswordGroupUseCase,
    private val getAllPasswordPairUseCase: GetAllPasswordPairUseCase,
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
            target = getAllPasswordPairUseCase.data,
            coroutineScope = viewModelScope,
        ) { showUiState, state ->
            showUiState.copy(
                contents = showUiState.contents.copyWithPasswordPairs(
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
        fetchPasswordPairs(passwordGroupId)
    }

    private fun fetchTitle(passwordGroupId: String) {
        viewModelScope.launch {
            getPasswordGroupUseCase.getById(passwordGroupId)
        }
    }

    private fun fetchPasswordPairs(passwordGroupId: String) {
        viewModelScope.launch {
            getAllPasswordPairUseCase.getAllByPasswordGroupId(passwordGroupId)
        }
    }

    fun changePasswordState(passwordPairId: String) =
        viewModelScope.launch {

            val newPasswordPairsState = _uiState.value
                .contents
                .passwordPairs
                .map { list ->
                    list.map {
                        if (it.id == passwordPairId) it.copyWithShowPassword(!it.showPassword) else it
                    }
                }

            val newUiState = _uiState.value.copy(
                contents = _uiState.value.contents.copyWithPasswordPairs(
                    passwords = newPasswordPairsState
                )
            )

            _uiState.emit(newUiState)
        }
}
