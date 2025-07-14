package org.catsproject.project.data.database.datasource

import kotlinx.coroutines.flow.Flow
import org.catsproject.project.data.database.models.CatResulEntity
import org.catsproject.project.data.database.models.CatsEntity

interface CatsDataSource {

    fun getAllCats(): List<CatsEntity>

    fun getCatsById(id: String,user: String): CatResulEntity?

    suspend fun saveCat(cat: CatsEntity): Result<Unit>

    suspend fun saveCats(cats: List<CatsEntity>): Result<Unit>

    suspend fun deleteCat(id: String): Result<Unit>

    suspend fun deleteAllCats(): Result<Unit>

    suspend fun getListCatsByPage(page:Int):List<CatsEntity>

    suspend fun getListFavoriteByUser(user: String):Flow<List<CatResulEntity>>

    suspend fun getListCatsCatalog(user: String,page: Int):Flow<List<CatResulEntity>>

    suspend fun deleteCatsPage(page: Int)

}