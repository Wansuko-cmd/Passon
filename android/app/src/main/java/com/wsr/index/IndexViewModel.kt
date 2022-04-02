package com.wsr.index

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wsr.create.CreatePasswordGroupUseCase
import com.wsr.get.GetAllPasswordGroupUseCase
import com.wsr.maybe.mapBoth
import com.wsr.utils.asState
import com.wsr.utils.consume
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class IndexViewModel(
    private val getAllPasswordGroupUseCase: GetAllPasswordGroupUseCase,
    private val createPasswordGroupUseCase: CreatePasswordGroupUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(IndexUiState())
    val uiState get() = _uiState.asStateFlow()

    private val _indexRefreshEvent = MutableSharedFlow<Unit>()
    val indexRefreshEvent = _indexRefreshEvent.asSharedFlow()

    private val _navigateToEditEvent = MutableSharedFlow<String>()
    val navigateToEditEvent = _navigateToEditEvent.asSharedFlow()

    fun fetch(userId: String) {
        viewModelScope.launch { _uiState.emit(IndexUiState()) }
        fetchPasswordGroups(userId)
    }

    private fun fetchPasswordGroups(userId: String) {
        viewModelScope.launch {
            getAllPasswordGroupUseCase.get(userId)
                .mapBoth(
                    success = { list -> list.map { it.toIndexUiState() } },
                    failure = { ErrorIndexUiState(it.message ?: "") },
                )
                .asState()
                .also { _uiState.update { current -> current.copy(passwordGroupsState = it) } }
        }
    }

    fun createPasswordGroup(userId: String, title: String, shouldNavigateToEdit: Boolean) {
        viewModelScope.launch {
            createPasswordGroupUseCase.create(userId, title)
                .asState()
                .consume(
                    success = {
                        if (shouldNavigateToEdit) _navigateToEditEvent.emit(it.id)
                        else _indexRefreshEvent.emit(Unit)
                    },
                    failure = { /* do nothing */ },
                    loading = { /* do nothing */ },
                )
        }
    }
}
