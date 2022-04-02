package com.wsr.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wsr.auth.SignUpUseCase
import com.wsr.maybe.consume
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class SignUpViewModel(private val signUpUseCase: SignUpUseCase) : ViewModel() {

    private val _displayName = MutableStateFlow("")
    private val _loginPassword = MutableStateFlow("")

    private val _navigateToIndexEvent = MutableSharedFlow<Unit>(replay = 0)
    val navigateToIndexEvent get() = _navigateToIndexEvent.asSharedFlow()

    fun updateDisplayName(displayName: String) {
        viewModelScope.launch { _displayName.emit(displayName) }
    }

    fun updateLoginPassword(loginPassword: String) {
        viewModelScope.launch { _loginPassword.emit(loginPassword) }
    }

    fun signUp() {
        viewModelScope.launch {
            signUpUseCase.signUp(
                displayName = _displayName.value,
                databasePath = "Local",
                loginPassword = _loginPassword.value,
            ).consume { _navigateToIndexEvent.emit(Unit) }
        }
    }
}
