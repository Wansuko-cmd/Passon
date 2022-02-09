package com.wsr.index

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wsr.passwordgroup.GetPasswordGroupUseCase
import com.wsr.state.mapBoth
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class IndexViewModel : ViewModel(), KoinComponent {

    private val getPasswordGroupUseCase by inject<GetPasswordGroupUseCase>()

    val uiState = flowOf(IndexUiState())
        .combine(getPasswordGroupUseCase.data) { uiState, state ->
            Log.d("UI_STATE", "OUT")
            uiState.copy(
                passwordGroupsState = state.mapBoth(
                    success = { list -> list.map { it.toIndexUiState() } },
                    failure = { ErrorIndexUiState(it.message ?: "") }
                ),
            )
        }

    fun fetchPasswordGroup(email: String) {
        viewModelScope.launch {
            getPasswordGroupUseCase.getAllByEmail(email)
        }
    }
}