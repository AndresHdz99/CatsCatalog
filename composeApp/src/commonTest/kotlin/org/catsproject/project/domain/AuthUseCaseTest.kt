package org.catsproject.project.domain

import org.catsproject.project.data.repository.AuthRepository
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class AuthUseCaseTest {

    private val repository = AuthRepository()
    private val useCase = AuthUseCase(repository)

    @Test
    fun `login returns token for valid admin credentials`() {
        val username = "admin"
        val password = "admin123"

        val token = useCase.login(username, password)

        assertNotNull(token, "Debe regresar un token para credenciales válidas")
        assertTrue(token.startsWith("fake_token_admin_"), "El token debe comenzar con 'fake_token_admin_'")
    }

    @Test
    fun `login returns token for valid user123 credentials`() {
        val username = "user123"
        val password = "user123"

        val token = useCase.login(username, password)

        assertNotNull(token, "Debe regresar un token para credenciales válidas")
        assertTrue(token.startsWith("fake_token_user123_"), "El token debe comenzar con 'fake_token_user123_'")
    }

    @Test
    fun `login returns null for invalid credentials`() {
        val username = "admin"
        val password = "wrongpassword"

        val token = useCase.login(username, password)

        assertNull(token, "Debe regresar null para credenciales inválidas")
    }

    @Test
    fun `login returns null for unknown user`() {
        val username = "unknown"
        val password = "password"

        val token = useCase.login(username, password)

        assertNull(token, "Debe regresar null para usuario inexistente")
    }
}
