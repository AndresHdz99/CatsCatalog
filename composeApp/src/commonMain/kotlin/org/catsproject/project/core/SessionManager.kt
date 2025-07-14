package org.catsproject.project.core

import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import com.russhwolf.settings.get
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SessionManager(
    private val settings: Settings,
    private val resetManager: ViewModelResetManager
) {
    companion object {
        private const val KEY_TOKEN = "auth_token"
        private const val KEY_USERNAME = "auth_username"
    }

    private val _currentUsername = MutableStateFlow(getUsername())
   // val currentUsername = _currentUsername.asStateFlow()

    fun saveSession(token: String, username: String) {
        val previousUsername = getUsername()

        settings[KEY_TOKEN] = token
        settings[KEY_USERNAME] = username
        _currentUsername.value = username

        if (previousUsername != username) {
            resetManager.notifyUserChanged(username)
        }
    }


    private fun getToken(): String? = settings[KEY_TOKEN]
    fun getUsername(): String = settings[KEY_USERNAME] ?: ""
    fun isLoggedIn(): Boolean = getToken() != null

    fun logout() {
        settings.remove(KEY_TOKEN)
        settings.remove(KEY_USERNAME)
        _currentUsername.value = ""
        resetManager.notifyUserChanged("")
    }
}