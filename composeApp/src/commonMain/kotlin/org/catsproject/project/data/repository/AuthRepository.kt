package org.catsproject.project.data.repository


class AuthRepository {

    private val users = mapOf(
        "admin" to "admin123",
        "user123" to "user123"
    )

    fun login(username: String, password: String): String? {
        return if (users[username] == password) {
            "fake_token_${username}_${System.currentTimeMillis()}"
        } else null
    }
}
