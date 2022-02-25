package com.wsr.index

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wsr.passwordgroup.create.CreatePasswordGroupUseCase
import com.wsr.passwordgroup.getall.GetAllPasswordGroupUseCase
import com.wsr.state.mapBoth
import kotlinx.coroutines.Dispatchers
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

    fun fetch(email: String) = fetchPasswordGroups(email)

    private fun fetchPasswordGroups(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getAllPasswordGroupUseCase.getAllByEmail(email)
        }
    }

    suspend fun create(email: String, title: String) = viewModelScope.launch(Dispatchers.IO) {
        createPasswordGroupUseCase.create(email, title)
    }
}