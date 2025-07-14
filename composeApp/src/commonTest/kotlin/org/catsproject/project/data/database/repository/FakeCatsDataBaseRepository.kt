package org.catsproject.project.data.database.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.catsproject.project.data.database.models.CatResulEntity
import org.catsproject.project.data.database.models.CatsEntity

class FakeCatsDataBaseRepository : CatsDataBaseRepository {

    val cats = mutableListOf<CatsEntity>()
    val favoriteCats = mutableListOf<CatResulEntity>()

    override fun getAllCats(): List<CatsEntity> = cats

    override suspend fun getCatById(id: String, user: String): CatResulEntity? {
        return favoriteCats.find { it._id == id }
    }

    override suspend fun saveCat(cat: CatsEntity): Result<Unit> {
        cats.add(cat)
        return Result.success(Unit)
    }

    override suspend fun saveCats(cats: List<CatsEntity>): Result<Unit> {
        this.cats.addAll(cats)
        return Result.success(Unit)
    }

    override suspend fun deleteCat(id: String): Result<Unit> {
        val removed = cats.removeIf { it._id == id }
        return if (removed) Result.success(Unit) else Result.failure(Exception("Not found"))
    }

    override suspend fun deleteAllCats(): Result<Unit> {
        cats.clear()
        return Result.success(Unit)
    }

    override suspend fun getListCatsByPage(page: Int): List<CatsEntity> {
        return cats.filter { it.page == page }
    }

    override suspend fun getListFavoriteByUser(user: String): Flow<List<CatResulEntity>> {
        return flowOf(favoriteCats)
    }

    override suspend fun getListCatsCatalog(user: String, page: Int): Flow<List<CatResulEntity>> {
        return flowOf(favoriteCats.filter { it.page == page })
    }

    override suspend fun deleteCatsPage(page: Int) {
        cats.removeIf { it.page == page }
    }
}
