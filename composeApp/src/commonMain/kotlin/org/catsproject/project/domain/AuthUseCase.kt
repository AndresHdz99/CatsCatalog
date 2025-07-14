package org.catsproject.project.domain

import org.catsproject.project.data.repository.AuthRepository

class AuthUseCase(private val repository: AuthRepository) {
    fun login(username: String, password: String): String? {
        return repository.login(username, password)
    }
}