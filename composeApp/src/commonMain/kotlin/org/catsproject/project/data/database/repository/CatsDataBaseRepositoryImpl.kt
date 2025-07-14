package org.catsproject.project.data.database.repository

import kotlinx.coroutines.flow.Flow
import org.catsproject.project.data.database.datasource.CatsDataSource
import org.catsproject.project.data.database.models.CatResulEntity
import org.catsproject.project.data.database.models.CatsEntity

class CatsDataBaseRepositoryImpl(
    private val catsDataSource: CatsDataSource
) : CatsDataBaseRepository {

    override fun getAllCats(): List<CatsEntity> {
        return catsDataSource.getAllCats()
    }

    override suspend fun getCatById(id: String, user: String): CatResulEntity? {
        return catsDataSource.getCatsById(id,user)
    }

    override suspend fun saveCat(cat: CatsEntity): Result<Unit> {
        return catsDataSource.saveCat(cat)
    }

    override suspend fun saveCats(cats: List<CatsEntity>): Result<Unit> {
        return catsDataSource.saveCats(cats)
    }

    override suspend fun deleteCat(id: String): Result<Unit> {
        return catsDataSource.deleteCat(id)
    }

    override suspend fun deleteAllCats(): Result<Unit> {
        return catsDataSource.deleteAllCats()
    }

    override suspend fun getListCatsByPage(page: Int): List<CatsEntity> {
        return catsDataSource.getListCatsByPage(page)
    }

    override suspend fun getListFavoriteByUser(user: String): Flow<List<CatResulEntity>> {
        return  catsDataSource.getListFavoriteByUser(user)
    }

    override suspend fun getListCatsCatalog(user: String, page: Int): Flow<List<CatResulEntity>> {
        return catsDataSource.getListCatsCatalog(user,page)
    }

    override suspend fun deleteCatsPage(page: Int) {
        catsDataSource.deleteCatsPage(page)
    }


}