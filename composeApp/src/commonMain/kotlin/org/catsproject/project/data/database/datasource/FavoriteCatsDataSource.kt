package org.catsproject.project.data.database.datasource

import kotlinx.coroutines.flow.Flow
import org.catsproject.project.data.database.models.CatResulEntity

interface FavoriteCatsDataSource {


    suspend fun addFavoriteByUser(user:String,idCat:String)

    suspend fun deleteFavoriteByUser(user:String,idCat:String)

    suspend fun getListMyCatsFavorite(user: String):Flow<List<CatResulEntity>>

}