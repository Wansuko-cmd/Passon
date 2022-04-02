package com.wsr.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wsr.auth.SignUpUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SignUpViewModel(private val signUpUseCase: SignUpUseCase) : ViewModel() {

    private val _displayName = MutableStateFlow("")
    private val _loginPassword = MutableStateFlow("")

    fun updateDisplayName(displayName: String) {
        viewModelScope.launch { _displayName.emit(displayName) }
    }

    fun updateLoginPassword(loginPassword: String) {
        viewModelScope.launch { _loginPassword.emit(loginPassword) }
    }

    fun signUp() {
        viewModelScope.launch {
            signUpUseCase.signUp(_displayName.value, "Local", _loginPassword.value)
        }
    }
}
