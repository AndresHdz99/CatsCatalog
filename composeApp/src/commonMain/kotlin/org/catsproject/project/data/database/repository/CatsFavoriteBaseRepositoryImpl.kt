package org.catsproject.project.data.database.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import org.catsproject.project.data.database.datasource.FavoriteCatsDataSource
import org.catsproject.project.data.database.models.CatResulEntity

class CatsFavoriteBaseRepositoryImpl(
    private val favoriteCatsDataSource: FavoriteCatsDataSource
):CatsFavoriteBaseRepository {

    private val _favoriteCatsUpdated = MutableSharedFlow<String>()
    override val favoriteCatsUpdated: SharedFlow<String> = _favoriteCatsUpdated.asSharedFlow()

    override suspend fun addFavoriteByUser(user: String, idCat: String) {
        favoriteCatsDataSource.addFavoriteByUser(user,idCat)
        _favoriteCatsUpdated.emit(idCat)
    }

    override suspend fun deleteFavoriteByUser(user: String, idCat: String) {
        favoriteCatsDataSource.deleteFavoriteByUser(user, idCat)
        _favoriteCatsUpdated.emit(idCat)
    }

    override suspend fun getListMyCatsFavorite(user: String): Flow<List<CatResulEntity>> {
        return favoriteCatsDataSource.getListMyCatsFavorite(user)
    }
}