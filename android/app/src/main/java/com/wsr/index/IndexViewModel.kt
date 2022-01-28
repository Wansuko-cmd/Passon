package com.wsr.index

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.michaelbull.result.map
import com.github.michaelbull.result.mapBoth
import com.wsr.passwordgroup.GetPasswordGroupUseCase
import com.wsr.utils.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class IndexViewModel : ViewModel() {

    private val getPasswordGroupUseCase = GetPasswordGroupUseCase()

    private val _uiState = MutableStateFlow(IndexUiState())
    val uiState = _uiState.asStateFlow()

    fun fetchPasswordGroup(email: String) {
        viewModelScope.launch {
            getPasswordGroupUseCase
                .getAllByEmail(email)
                .map { list -> list.map { it.toIndexUiState() } }
                .mapBoth(
                    success = { list ->
                        _uiState.update {
                            it.copy(passwordGroups = State.Success(list))
                        }
                    },
                    failure = { error ->
                        _uiState.update {
                            it.copy(
                                passwordGroups = State.Failure(
                                    ErrorIndexUiState(
                                        error.message ?: ""
                                    )
                                )
                            )
                        }
                    }
                )
        }
    }
}