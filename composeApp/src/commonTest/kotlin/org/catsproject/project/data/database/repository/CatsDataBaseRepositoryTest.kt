package org.catsproject.project.data.database.repository

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.catsproject.project.data.database.models.CatResulEntity
import org.catsproject.project.data.database.models.CatsEntity
import kotlin.test.*

class CatsDataBaseRepositoryTest {

    @Test
    fun `save and get cat by id works`() = runTest {
        val repo = FakeCatsDataBaseRepository()

        val catResult = CatResulEntity(
            _id = "abc123",
            url = "https://cat.png",
            name = "Mishi",
            temperament = "Calm",
            description = "A nice cat",
            energyLevel = 3,
            intelligence = 5,
            page = 1,
            favorite = true,
            origin = "Nowhere",
            life_span = "10 years"
        )

        repo.favoriteCats.add(catResult)

        val result = repo.getCatById("abc123", "user1")

        assertNotNull(result)
        assertEquals("Mishi", result?.name)
    }

    @Test
    fun `delete cat by id works`() = runTest {
        val repo = FakeCatsDataBaseRepository()
        val cat = CatsEntity().apply { _id = "cat123"; page = 1 }

        repo.saveCat(cat)
        assertTrue(repo.getAllCats().any { it._id == "cat123" })

        repo.deleteCat("cat123")
        assertFalse(repo.getAllCats().any { it._id == "cat123" })
    }

    @Test
    fun `get cats by page returns correct cats`() = runTest {
        val repo = FakeCatsDataBaseRepository()

        val cat1 = CatsEntity().apply { _id = "1"; page = 1 }
        val cat2 = CatsEntity().apply { _id = "2"; page = 2 }
        val cat3 = CatsEntity().apply { _id = "3"; page = 1 }

        repo.saveCats(listOf(cat1, cat2, cat3))

        val page1Cats = repo.getListCatsByPage(1)
        assertEquals(2, page1Cats.size)
        assertTrue(page1Cats.all { it.page == 1 })
    }

    @Test
    fun `get favorites by user returns flow of favorites`() = runTest {
        val repo = FakeCatsDataBaseRepository()

        val catResult = CatResulEntity(
            _id = "abc123",
            url = "https://cat.png",
            name = "Mishi",
            temperament = "Calm",
            description = "A nice cat",
            energyLevel = 3,
            intelligence = 5,
            page = 1,
            favorite = true,
            origin = "Nowhere",
            life_span = "10 years"
        )

        repo.favoriteCats.add(catResult)

        val favoritesFlow = repo.getListFavoriteByUser("anyUser")
        val favorites = favoritesFlow.first()

        assertEquals(1, favorites.size)
        assertEquals("Mishi", favorites.first().name)
    }
}
