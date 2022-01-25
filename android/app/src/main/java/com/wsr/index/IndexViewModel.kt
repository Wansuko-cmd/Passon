package com.wsr.index

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wsr.passwordgroup.GetPasswordGroupUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class IndexViewModel : ViewModel() {

    private val getPasswordGroupUseCase = GetPasswordGroupUseCase()

    private val _uiState = MutableStateFlow(IndexUIState())
    val uiState = _uiState.asStateFlow()

    fun fetchPasswordGroup(email: String) {
        viewModelScope.launch {
            val passwordGroups = getPasswordGroupUseCase
                .getAllByEmail(email)
                .map { it.toIndexUIState() }

            _uiState.update { it.copy(isFetching = true, passwordGroups = passwordGroups) }
        }
    }
}