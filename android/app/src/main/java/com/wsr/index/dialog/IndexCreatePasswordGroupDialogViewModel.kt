package com.wsr.index.dialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class IndexCreatePasswordGroupDialogViewModel : ViewModel() {


    private val _uiState = MutableStateFlow(IndexCreatePasswordGroupDialogUiState())
    val title get() = _uiState.value.title
    val goToEdit get() = _uiState.value.goToEdit

    fun updateTitle(title: String) = viewModelScope.launch { _uiState.emit(_uiState.value.copyWithTitle(title)) }
    fun changeChecked() = viewModelScope.launch { _uiState.emit(_uiState.value.changeChecked()) }
}

data class IndexCreatePasswordGroupDialogUiState(
    val title: String = "",
    val goToEdit: Boolean = true,
) {
    fun copyWithTitle(title: String) = this.copy(title = title)
    fun changeChecked() = this.copy(goToEdit = !goToEdit)
}