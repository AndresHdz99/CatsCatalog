package org.catsproject.project.data.network.domain


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.catsproject.project.data.database.models.CatResulEntity
import org.catsproject.project.data.database.models.CatsEntity
import org.catsproject.project.data.database.repository.CatsDataBaseRepository
import org.catsproject.project.data.network.domain.GetCatsCatalogCatsApi
import org.catsproject.project.data.network.networkmodel.Breed
import org.catsproject.project.data.network.networkmodel.CatsModelServicesItem
import org.catsproject.project.data.network.repository.CatsCatalogRepository
import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.test.*



class FakeCatsCatalogImpl(
    private val catsToReturn: List<CatsModelServicesItem> = emptyList()
) : CatsCatalogRepository {
    override suspend fun fetchCats(page: Int): Result<List<CatsModelServicesItem>> {
        return Result.success(catsToReturn)
    }
}

// Extensión para simular toServiceByDB (convierte CatsModelServicesItem a CatsEntity)
fun CatsModelServicesItem.toServiceByDB(page: Int): CatsEntity {
    val catEntity = CatsEntity()
    catEntity._id = this.id
    catEntity.name = this.breeds.firstOrNull()?.name ?: ""
    catEntity.temperament = this.breeds.firstOrNull()?.temperament ?: ""
    catEntity.description = this.breeds.firstOrNull()?.description ?: ""
    catEntity.energyLevel = this.breeds.firstOrNull()?.energy_level ?: 0
    catEntity.intelligence = this.breeds.firstOrNull()?.intelligence ?: 0
    catEntity.page = page
    catEntity.origin = this.breeds.firstOrNull()?.origin ?: ""
    catEntity.life_span = this.breeds.firstOrNull()?.life_span ?: ""
    catEntity.url = this.url
    return catEntity
}

class GetCatsCatalogCatsApiTest {

    @Test
    fun `invoke with empty list should not delete or save`() = runTest {
        val fakeCatalog = FakeCatsCatalogImpl(emptyList())
        val fakeDb = FakeCatsDataBaseRepositoryImpl()

        val useCase = GetCatsCatalogCatsApi(fakeCatalog, fakeDb)

        val result = useCase.invoke(1)

        assertTrue(result.isSuccess)
        assertNull(fakeDb.deletedPage)
        assertNull(fakeDb.savedCats)
    }

    @Test
    fun `invoke with non-empty list should delete and save`() = runTest {
        val dummyBreed = Breed(
            child_friendly = 5,
            description = "Friendly cat",
            energy_level = 4,
            intelligence = 3,
            name = "DummyBreed",
            social_needs = 3,
            temperament = "Calm",
            origin = "Nowhere",
            life_span = "10-12 years"
        )
        val catItem = CatsModelServicesItem(
            breeds = listOf(dummyBreed),
            height = 100,
            id = "cat1",
            url = "https://example.com/cat1.jpg",
            width = 100
        )

        val fakeCatalog = FakeCatsCatalogImpl(listOf(catItem))
        val fakeDb = FakeCatsDataBaseRepositoryImpl()

        val useCase = GetCatsCatalogCatsApi(fakeCatalog, fakeDb)

        val result = useCase.invoke(1)

        assertTrue(result.isSuccess)
        assertEquals(1, fakeDb.deletedPage)
        assertNotNull(fakeDb.savedCats)
        assertEquals("cat1", fakeDb.savedCats?.firstOrNull()?._id)
    }
}
