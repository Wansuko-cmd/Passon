package com.wsr.index.dialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class IndexCreatePasswordGroupDialogViewModel : ViewModel() {

    private val _shouldNavigateToEdit = MutableStateFlow(false)
    val shouldNavigateToEdit get() = _shouldNavigateToEdit

    fun switchChecked() =
        viewModelScope.launch { _shouldNavigateToEdit.emit(!_shouldNavigateToEdit.value) }
}
