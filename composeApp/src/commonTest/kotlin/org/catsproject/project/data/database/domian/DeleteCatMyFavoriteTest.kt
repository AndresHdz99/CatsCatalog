package org.catsproject.project.data.database.domian


import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.catsproject.project.data.database.domin.DeleteCatMyFavorite
import org.catsproject.project.data.database.models.CatResulEntity
import org.catsproject.project.data.database.repository.CatsFavoriteBaseRepository
import kotlin.test.Test
import kotlin.test.assertTrue

class FakeCatsFavoriteBaseRepository : CatsFavoriteBaseRepository {
    var deletedUser: String? = null
    var deletedCatId: String? = null

    var addedUser: String? = null
    var addedCatId: String? = null

    private val _favoriteCatsUpdated = MutableSharedFlow<String>(replay = 1)
    override val favoriteCatsUpdated = _favoriteCatsUpdated.asSharedFlow()

    override suspend fun addFavoriteByUser(user: String, idCat: String) {
        addedUser = user
        addedCatId = idCat
        _favoriteCatsUpdated.emit(idCat)
    }

    override suspend fun deleteFavoriteByUser(user: String, idCat: String) {
        deletedUser = user
        deletedCatId = idCat
        _favoriteCatsUpdated.emit(idCat)
    }

    override suspend fun getListMyCatsFavorite(user: String) = emptyFlow<List<CatResulEntity>>()
}


class DeleteCatMyFavoriteTest {

    @Test
    fun `invoke calls deleteFavoriteByUser and emits updated id`() = runTest {
        val fakeRepo = FakeCatsFavoriteBaseRepository()
        val useCase = DeleteCatMyFavorite(fakeRepo)

        val collectedIds = mutableListOf<String>()

        val job = launch {
            fakeRepo.favoriteCatsUpdated.collect { id ->
                collectedIds.add(id)
            }
        }

        useCase.invoke("testUser", "cat123")


        advanceUntilIdle()


        assertTrue(collectedIds.contains("cat123"), "El id esperado no fue emitido")

        job.cancel()
    }

}


