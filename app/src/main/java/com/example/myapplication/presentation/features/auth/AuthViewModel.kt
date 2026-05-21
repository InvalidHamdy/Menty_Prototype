package com.example.myapplication.presentation.features.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.model.UserSession
import com.example.myapplication.domain.usecase.GetSessionUseCase
import com.example.myapplication.domain.usecase.LoginUseCase
import com.example.myapplication.domain.usecase.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AuthUiState(
    val username: String = "",
    val pass: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val signUpUseCase: SignUpUseCase,
    getSessionUseCase: GetSessionUseCase
) : ViewModel() {

    val session: StateFlow<UserSession> = getSessionUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UserSession("", false)
        )

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState = _uiState.asStateFlow()

    fun onUsernameChange(value: String) {
        _uiState.value = _uiState.value.copy(username = value, error = null)
    }

    fun onPasswordChange(value: String) {
        _uiState.value = _uiState.value.copy(pass = value, error = null)
    }

    fun login() {
        val state = _uiState.value
        if (state.username.isBlank() || state.pass.length < 4) {
            _uiState.value = state.copy(error = "Username and Password must be 4+ chars")
            return
        }
        _uiState.value = state.copy(isLoading = true, error = null)
        viewModelScope.launch {
            loginUseCase(state.username, state.pass)
                .onSuccess {
                    _uiState.value = _uiState.value.copy(isLoading = false, isSuccess = true)
                }
                .onFailure {
                    _uiState.value = _uiState.value.copy(isLoading = false, error = it.message ?: "Authentication failed")
                }
        }
    }

    fun signUp() {
        val state = _uiState.value
        if (state.username.isBlank() || state.pass.length < 4) {
            _uiState.value = state.copy(error = "Username and Password must be 4+ chars")
            return
        }
        _uiState.value = state.copy(isLoading = true, error = null)
        viewModelScope.launch {
            signUpUseCase(state.username, state.pass)
                .onSuccess {
                    _uiState.value = _uiState.value.copy(isLoading = false, isSuccess = true)
                }
                .onFailure {
                    _uiState.value = _uiState.value.copy(isLoading = false, error = it.message ?: "Registration failed")
                }
        }
    }

    fun resetState() {
        _uiState.value = AuthUiState()
    }
}
