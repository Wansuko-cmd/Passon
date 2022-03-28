package com.wsr.index

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wsr.create.CreatePasswordGroupUseCase
import com.wsr.fetch.FetchAllPasswordGroupUseCase
import com.wsr.state.consume
import com.wsr.state.mapBoth
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class IndexViewModel(
    private val fetchAllPasswordGroupUseCase: FetchAllPasswordGroupUseCase,
    private val createPasswordGroupUseCase: CreatePasswordGroupUseCase,
) : ViewModel() {

    val uiState = flowOf(IndexUiState())
        .combine(fetchAllPasswordGroupUseCase.data) { uiState, state ->
            uiState.copy(
                passwordGroupsState = state.mapBoth(
                    success = { list -> list.map { it.toIndexUiState() } },
                    failure = { ErrorIndexUiState(it.message ?: "") },
                ),
            )
        }

    private val _indexRefreshEvent = MutableSharedFlow<Unit>()
    val indexRefreshEvent = _indexRefreshEvent.asSharedFlow()

    private val _navigateToEditEvent = MutableSharedFlow<String>()
    val navigateToEditEvent = _navigateToEditEvent.asSharedFlow()

    fun fetch(email: String) = fetchPasswordGroups(email)

    private fun fetchPasswordGroups(email: String) {
        viewModelScope.launch {
            fetchAllPasswordGroupUseCase.fetch(email)
        }
    }

    fun createPasswordGroup(email: String, title: String, shouldNavigateToEdit: Boolean) {
        viewModelScope.launch {
            createPasswordGroupUseCase.create(email, title).consume(
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
