package com.wsr.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wsr.auth.ResetLoginPasswordUseCase
import com.wsr.get.GetUserUseCase
import com.wsr.maybe.mapBoth
import com.wsr.update.UpdateUserUseCase
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val resetLoginPasswordUseCase: ResetLoginPasswordUseCase,
) : ViewModel() {

    suspend fun getDisplayName(userId: String) =
        getUserUseCase.get(userId).mapBoth(
            success = { it.toDisplayNameSettingsUiState() },
            failure = { ErrorSettingsUiState(message = it.message.orEmpty()) },
        )

    fun updateDisplayName(userId: String, displayName: String) {
        viewModelScope.launch {
            updateUserUseCase.update(userId, displayName)
        }
    }

    fun updateLoginPassword(
        userId: String,
        loginPassword: String,
        loginPasswordConfirmation: String,
    ) {
        viewModelScope.launch {
            if(loginPassword == loginPasswordConfirmation) resetLoginPasswordUseCase.reset(userId, loginPassword)
        }
    }
}
