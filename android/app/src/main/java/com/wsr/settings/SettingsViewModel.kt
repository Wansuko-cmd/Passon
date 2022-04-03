package com.wsr.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wsr.get.GetUserUseCase
import com.wsr.maybe.mapBoth
import com.wsr.utils.asState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(private val getUserUseCase: GetUserUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState get() = _uiState.asStateFlow()

    fun fetch(userId: String) {
        viewModelScope.launch { _uiState.emit(SettingsUiState()) }
        fetchUser(userId)
    }

    private fun fetchUser(userId: String) {
        viewModelScope.launch {
            getUserUseCase.get(userId).mapBoth(
                success = { it.toDisplayNameSettingsUiState() },
                failure = { ErrorSettingsUiState(it.message.orEmpty()) },
            )
                .asState()
                .also { _uiState.update { settingsUiState -> settingsUiState.copy(displayName = it) } }
        }
    }
}
