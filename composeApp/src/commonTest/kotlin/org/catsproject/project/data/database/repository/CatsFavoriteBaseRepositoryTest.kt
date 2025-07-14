package org.catsproject.project.data.database.repository


import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withTimeout
import org.catsproject.project.data.database.models.CatResulEntity
import kotlin.test.*

class CatsFavoriteBaseRepositoryTest {

    @Test
    fun `addFavoriteByUser should add favorite and emit update`() = runTest {
        val repo = FakeCatsFavoriteBaseRepository()
        val collectedIds = mutableListOf<String>()

        val job = launch {
            withTimeout(5000) {
                repo.favoriteCatsUpdated
                    .take(1)
                    .collect { collectedIds.add(it) }
            }
        }

        repo.addFavoriteByUser("user1", "cat123")

        advanceUntilIdle()

        assertTrue("cat123" in collectedIds)

        job.cancel()
    }





    @Test
    fun `deleteFavoriteByUser should remove favorite and emit update`() = runTest {
        val repo = FakeCatsFavoriteBaseRepository()
        repo.favorites.add("user1" to "cat123")

        val collectedIds = mutableListOf<String>()
        val job = launch {
            repo.favoriteCatsUpdated.collect { collectedIds.add(it) }
        }

        repo.deleteFavoriteByUser("user1", "cat123")

        advanceUntilIdle()

        assertFalse(repo.favorites.contains("user1" to "cat123"))
        assertTrue(collectedIds.contains("cat123"))

        job.cancel()
    }

    @Test
    fun `getListMyCatsFavorite should return favorites list`() = runTest {
        val repo = FakeCatsFavoriteBaseRepository()

        val catResult = CatResulEntity(
            _id = "abc123",
            url = "https://cat.png",
            name = "Gatito",
            temperament = "Calm",
            description = "Nice cat",
            energyLevel = 4,
            intelligence = 5,
            page = 1,
            favorite = true,
            origin = "Nowhere",
            life_span = "10 years"
        )

        repo.favoriteCats.add(catResult)

        val favoritesFlow = repo.getListMyCatsFavorite("anyUser")
        val favorites = favoritesFlow.first()

        assertEquals(1, favorites.size)
        assertEquals("Gatito", favorites.first().name)
    }
}
