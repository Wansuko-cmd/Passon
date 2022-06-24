package com.wsr.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.wsr.R
import com.wsr.auth.ResetLoginPasswordUseCase
import com.wsr.delete.DeleteUserUseCase
import com.wsr.get.GetUserUseCase
import com.wsr.maybe.consume
import com.wsr.maybe.mapBoth
import com.wsr.update.UpdateUserUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class SettingsViewModel(
    application: Application,
    private val getUserUseCase: GetUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val resetLoginPasswordUseCase: ResetLoginPasswordUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
) : AndroidViewModel(application) {

    private val _showMessageEvent = MutableSharedFlow<String>(replay = 1)
    val showMessageEvent get() = _showMessageEvent.asSharedFlow()

    suspend fun getDisplayName(userId: String) =
        getUserUseCase.get(userId).mapBoth(
            success = { it.toDisplayNameSettingsUiState() },
            failure = { ErrorSettingsUiState(message = it.message.orEmpty()) },
        )

    fun updateDisplayName(userId: String, displayName: String) {
        viewModelScope.launch {
            updateUserUseCase
                .update(userId, displayName)
                .consume(
                    success = {
                        _showMessageEvent.emit(getApplication<Application>().getString(R.string.settings_update_display_name_dialog_success_message))
                    },
                    failure = { _showMessageEvent.emit(it.message.orEmpty()) }
                )
        }
    }

    fun updateLoginPassword(
        userId: String,
        loginPassword: String,
        loginPasswordConfirmation: String,
    ) {
        viewModelScope.launch {
            if (loginPassword == loginPasswordConfirmation)
                resetLoginPasswordUseCase
                    .reset(userId, loginPassword)
                    .consume(
                        success = {
                            _showMessageEvent.emit(getApplication<Application>().getString(R.string.settings_update_login_password_dialog_success_message))
                        },
                        failure = { _showMessageEvent.emit(it.message.orEmpty()) }
                    )
        }
    }

    fun deleteUser(userId: String) {
        viewModelScope.launch {
            deleteUserUseCase.delete(userId)
        }
    }
}
