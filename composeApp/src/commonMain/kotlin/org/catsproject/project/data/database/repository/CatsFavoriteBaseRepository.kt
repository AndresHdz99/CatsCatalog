package org.catsproject.project.data.database.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import org.catsproject.project.data.database.models.CatResulEntity

interface CatsFavoriteBaseRepository {

    val favoriteCatsUpdated: SharedFlow<String>

    suspend fun addFavoriteByUser(user:String,idCat:String)

    suspend fun deleteFavoriteByUser(user:String,idCat:String)

    suspend fun getListMyCatsFavorite(user: String):Flow<List<CatResulEntity>>
}