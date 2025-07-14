package org.catsproject.project.data.database.domian

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import org.catsproject.project.data.database.domian.FakeCatsDataBaseRepositoryImpl
import org.catsproject.project.data.database.models.CatResulEntity
import org.catsproject.project.data.database.models.CatsEntity
import org.catsproject.project.data.database.repository.CatsDataBaseRepository

class GetCatInformationDBTest {

    private val fakeRepository = FakeCatsDataBaseRepositoryImpl()

    @Test
    fun `test getCatById returns correct cat`() = runTest {

        val catId = "cat123"
        val user = "testUser"


        val cat = fakeRepository.getCatById(catId, user)


        assertEquals(catId, cat?._id)
        assertEquals("Gatito", cat?.name)
        assertEquals(true, cat?.favorite)
    }

    @Test
    fun `test getCatById returns null for unknown cat`() = runTest {

        val catId = "unknown"
        val user = "testUser"


        val cat = fakeRepository.getCatById(catId, user)


         assertEquals(null, cat)
    }
}

class FakeCatsDataBaseRepositoryImpl : CatsDataBaseRepository {

    override suspend fun getCatById(id: String, user: String): CatResulEntity? {
        return if (id == "cat123") {
            CatResulEntity(
                _id = id,
                url = "https://example.com/gatito.png",
                name = "Gatito",
                temperament = "Tranquilo",
                description = "Un gato feliz de prueba",
                energyLevel = 5,
                intelligence = 8,
                page = 1,
                favorite = true,
                origin = "México",
                life_span = "12 años"
            )
        } else {
            null
        }
    }

    override fun getAllCats(): List<CatsEntity> {
        // Puedes devolver lista vacía para evitar errores
        return emptyList()
    }

    override suspend fun saveCat(cat: CatsEntity): Result<Unit> {
        return Result.success(Unit)
    }

    override suspend fun saveCats(cats: List<CatsEntity>): Result<Unit> {
        return Result.success(Unit)
    }

    override suspend fun deleteCat(id: String): Result<Unit> {
        return Result.success(Unit)
    }

    override suspend fun deleteAllCats(): Result<Unit> {
        return Result.success(Unit)
    }

    override suspend fun getListCatsByPage(page: Int): List<CatsEntity> {
        return emptyList()
    }

    override suspend fun getListFavoriteByUser(user: String): Flow<List<CatResulEntity>> {
        return kotlinx.coroutines.flow.flowOf(emptyList())
    }

    override suspend fun getListCatsCatalog(user: String, page: Int):Flow<List<CatResulEntity>> {
        return kotlinx.coroutines.flow.flowOf(emptyList())
    }

    override suspend fun deleteCatsPage(page: Int) {

    }
}
