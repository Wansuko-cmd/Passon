package com.wsr.settings

import androidx.lifecycle.ViewModel
import com.wsr.get.GetUserUseCase
import com.wsr.maybe.mapBoth

class SettingsViewModel(private val getUserUseCase: GetUserUseCase) : ViewModel() {

    suspend fun getDisplayName(userId: String) =
        getUserUseCase.get(userId).mapBoth(
            success = { it.toDisplayNameSettingsUiState() },
            failure = { ErrorSettingsUiState(message = it.message.orEmpty()) },
        )
}
