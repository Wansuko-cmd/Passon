package com.wsr.index

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wsr.passwordgroup.create.CreatePasswordGroupUseCase
import com.wsr.passwordgroup.getall.GetAllPasswordGroupUseCase
import com.wsr.state.consume
import com.wsr.state.mapBoth
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class IndexViewModel(
    private val getAllPasswordGroupUseCase: GetAllPasswordGroupUseCase,
    private val createPasswordGroupUseCase: CreatePasswordGroupUseCase,
) : ViewModel() {

    val uiState = flowOf(IndexUiState())
        .combine(getAllPasswordGroupUseCase.data) { uiState, state ->
            uiState.copy(
                passwordGroupsState = state.mapBoth(
                    success = { list -> list.map { it.toIndexUiState() } },
                    failure = { ErrorIndexUiState(it.message ?: "") },
                ),
            )
        }

    private val _indexRefreshEvent = MutableSharedFlow<IndexRefreshEvent>()
    val indexRefreshEvent = _indexRefreshEvent.asSharedFlow()

    fun fetch(email: String) = fetchPasswordGroups(email)

    private fun fetchPasswordGroups(email: String) {
        viewModelScope.launch {
            getAllPasswordGroupUseCase.getAllByEmail(email)
        }
    }

    fun createPasswordGroup(email: String, title: String, shouldNavigateToEdit: Boolean) {
        viewModelScope.launch {
            createPasswordGroupUseCase.create(email, title).consume(
                success = {
                    val navigateToEditEvent =
                        if (shouldNavigateToEdit) NavigateToEditEvent.True(it.id)
                        else NavigateToEditEvent.False
                    _indexRefreshEvent.emit(IndexRefreshEvent(navigateToEditEvent))
                },
                failure = {},
                loading = {},
            )
        }
    }
}
