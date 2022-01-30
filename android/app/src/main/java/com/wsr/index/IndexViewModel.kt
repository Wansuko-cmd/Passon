package com.wsr.index

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.michaelbull.result.map
import com.github.michaelbull.result.mapOrElse
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

            val passwordGroupState = getPasswordGroupUseCase
                .getAllByEmail(email)
                .map { list -> list.map { it.toIndexUiState() } }
                .mapOrElse(
                    transform = { list -> State.Success(list) },
                    default = { error -> State.Failure(ErrorIndexUiState(error.message ?: "")) }
                )

            _uiState.update { it.copy(passwordGroupsState = passwordGroupState) }
        }
    }
}