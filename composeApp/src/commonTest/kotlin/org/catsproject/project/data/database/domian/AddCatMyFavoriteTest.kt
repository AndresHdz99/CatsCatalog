package org.catsproject.project.data.database.domian

import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.catsproject.project.data.database.domin.AddCatMyFavorite
import kotlin.test.Test
import kotlin.test.assertTrue

class AddCatMyFavoriteTest {

    @Test
    fun `invoke calls addFavoriteByUser and emits updated id`() = runTest {
        val fakeRepo = FakeCatsFavoriteBaseRepository()
        val useCase = AddCatMyFavorite(fakeRepo)

        val collectedIds = mutableListOf<String>()

        val job = launch {
            fakeRepo.favoriteCatsUpdated.collect {
                collectedIds.add(it)
            }
        }

        useCase.invoke("testUser", "cat456")

        advanceUntilIdle()

        assertTrue(collectedIds.contains("cat456"), "El id esperado no fue emitido")

        job.cancel()
    }
}
