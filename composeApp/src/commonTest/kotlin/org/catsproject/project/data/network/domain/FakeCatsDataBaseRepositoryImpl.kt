package org.catsproject.project.data.network.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.catsproject.project.data.database.models.CatResulEntity
import org.catsproject.project.data.database.models.CatsEntity
import org.catsproject.project.data.database.repository.CatsDataBaseRepository

class FakeCatsDataBaseRepositoryImpl(
    private val cats: MutableList<CatsEntity> = mutableListOf(),
    private val catsResult: MutableList<CatResulEntity> = mutableListOf()
) : CatsDataBaseRepository
{

    var deletedPage: Int? = null
    var savedCats: List<CatsEntity>? = null

    var emittedUser: String? = null
    var emittedPage: Int? = null
    var catsToReturn: List<CatResulEntity> = emptyList()

    override fun getAllCats(): List<CatsEntity> = cats

    override suspend fun getCatById(id: String, user: String): CatResulEntity? {
        return catsResult.find { it._id == id }
    }

    override suspend fun saveCat(cat: CatsEntity): Result<Unit> {
        cats.add(cat)
        return Result.success(Unit)
    }

    override suspend fun saveCats(cats: List<CatsEntity>): Result<Unit> {
        savedCats = cats
        this.cats.addAll(cats)
        return Result.success(Unit)
    }

    override suspend fun deleteCat(id: String): Result<Unit> {
        val removed = cats.removeIf { it._id == id }
        return if (removed) Result.success(Unit) else Result.failure(Exception("Cat not found"))
    }

    override suspend fun deleteAllCats(): Result<Unit> {
        cats.clear()
        return Result.success(Unit)
    }

    override suspend fun getListCatsByPage(page: Int): List<CatsEntity> {
        return cats.filter { it.page == page }
    }

    override suspend fun getListFavoriteByUser(user: String) = flowOf(catsResult)

    override suspend fun getListCatsCatalog(user: String, page: Int): Flow<List<CatResulEntity>> {
        emittedUser = user
        emittedPage = page
        return flowOf(catsToReturn)
    }

    override suspend fun deleteCatsPage(page: Int) {
        deletedPage = page
        cats.removeIf { it.page == page }
    }
}