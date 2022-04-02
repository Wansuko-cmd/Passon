package com.wsr.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _enteredPassword = MutableStateFlow("")

    private val _checkPasswordEvent = MutableSharedFlow<Boolean>(replay = 0)
    val checkPasswordEvent = _checkPasswordEvent.asSharedFlow()

    private val _shouldPassEvent = MutableSharedFlow<Boolean>(replay = 0)
    val shouldPassEvent = _shouldPassEvent.asSharedFlow()

    fun updateEnteredPassword(enteredPassword: String) {
        viewModelScope.launch {
            _enteredPassword.emit(enteredPassword)
        }
    }

    fun checkPassword() {
        viewModelScope.launch {
            if (_enteredPassword.value == "Password") {
                _shouldPassEvent.emit(true)
                _checkPasswordEvent.emit(true)
            } else _shouldPassEvent.emit(false)
        }
    }
}
