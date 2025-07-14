package org.catsproject.project.ui.viewmodel

import androidx.compose.runtime.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import org.catsproject.project.core.BaseViewModel
import org.catsproject.project.core.SessionManager
import org.catsproject.project.core.ViewModelResetManager
import org.catsproject.project.data.model.LoginUiState
import org.catsproject.project.domain.AuthUseCase
import org.koin.mp.KoinPlatform

class LoginViewModel(
    private val authUseCase: AuthUseCase,
    private val sessionManager: SessionManager
) : BaseViewModel() {

    private val resetManager: ViewModelResetManager = KoinPlatform.getKoin().get()

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    var errorMessage by mutableStateOf<String?>(null)
    var isLoggedIn by mutableStateOf(sessionManager.isLoggedIn())

    init {
        observeUserChanges(resetManager)
    }

    fun onUserChange(user: String) {
        _uiState.update {
            it.copy(user = user)
        }
        verifyLogin()
    }

    fun onPasswordChange(password: String) {
        _uiState.update {
            it.copy(password = password)
        }
        verifyLogin()
    }

    private fun verifyLogin() {
        val enableLogin = isUserValid(_uiState.value.user) && isPasswordValid(_uiState.value.password)
        _uiState.update {
            it.copy(inLoginEnable = enableLogin)
        }
    }

    private fun isUserValid(user: String): Boolean = user.length >= 5
    private fun isPasswordValid(password: String): Boolean = password.length >= 5

    fun login() {
        val token = authUseCase.login(_uiState.value.user, _uiState.value.password)
        if (token != null) {
            sessionManager.saveSession(token, _uiState.value.user)
            isLoggedIn = true
            errorMessage = null
        } else {
            errorMessage = "Usuario o contraseña incorrectos"
        }
    }

    fun logout() {
        sessionManager.logout()
        isLoggedIn = false
    }

    override fun resetState() {
        _uiState.value = LoginUiState()
        errorMessage = null

    }
}
