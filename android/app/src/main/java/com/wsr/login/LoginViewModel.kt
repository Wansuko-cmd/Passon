package com.wsr.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wsr.auth.LoginUseCase
import com.wsr.get.GetAllUserUseCase
import com.wsr.maybe.consume
import com.wsr.maybe.map
import com.wsr.maybe.mapBoth
import com.wsr.utils.State
import com.wsr.utils.asState
import com.wsr.utils.consume
import com.wsr.utils.map
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val getAllUserUseCase: GetAllUserUseCase,
    private val loginUseCase: LoginUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState get() = _uiState.asStateFlow()

    private val _enteredPassword = MutableStateFlow("")

    private val _shouldPassEvent = MutableSharedFlow<Boolean>(replay = 0)
    val shouldPassEvent = _shouldPassEvent.asSharedFlow()

    private val _navigateToIndex = MutableSharedFlow<String>(replay = 0)
    val navigateToIndex get() = _navigateToIndex.asSharedFlow()

    private val _navigateToSignUp = MutableSharedFlow<Unit>(replay = 0)
    val navigateToSignUp get() = _navigateToSignUp.asSharedFlow()

    fun fetch(beforeUsedUserId: String? = null) {
        viewModelScope.launch { _uiState.emit(LoginUiState()) }
        fetchUsers(beforeUsedUserId)
    }

    private fun fetchUsers(beforeUsedUserId: String?) {
        viewModelScope.launch {
            val users = getAllUserUseCase.getAll().mapBoth(
                success = { users -> users.map { it.toLoginUiState() } },
                failure = { ErrorLoginUiState(it.message.orEmpty()) },
            )
                .map { users -> users.copyWithSelected(beforeUsedUserId ?: "") }
                .asState()

            users.consume(success = { if (it.isEmpty()) _navigateToSignUp.emit(Unit) })

            _uiState.update { current -> current.copy(users = users) }
        }
    }

    fun updateEnteredPassword(enteredPassword: String) {
        viewModelScope.launch {
            _enteredPassword.emit(enteredPassword)
        }
    }

    fun updateIsSelected(userId: String) {
        viewModelScope.launch {
            _uiState.update { loginUiState ->
                loginUiState.copyWithUsers(
                    users = loginUiState.users
                        .map { users -> users.copyWithSelected(userId) }
                )
            }
        }
    }

    fun checkPassword() {
        viewModelScope.launch {
            val user = _uiState.value.users.map { it.getSelected() }
            if (user is State.Success && user.value != null) {
                loginUseCase.shouldPass(
                    userId = user.value.id,
                    password = _enteredPassword.value,
                ).consume(
                    success = {
                        _shouldPassEvent.emit(true)
                        _navigateToIndex.emit(user.value.id)
                    },
                    failure = { _shouldPassEvent.emit(false) }
                )
            } else _shouldPassEvent.emit(false)
        }
    }

    fun passAuthentication() {
        viewModelScope.launch {
            _uiState.value
                .users
                .map { it.getSelected() }
                .consume(
                    success = {
                        if(it == null) _shouldPassEvent.emit(false)
                        else {
                            _shouldPassEvent.emit(true)
                            _navigateToIndex.emit(it.id)
                        }
                    },
                    failure = { _shouldPassEvent.emit(false) }
                )
        }
    }
}
