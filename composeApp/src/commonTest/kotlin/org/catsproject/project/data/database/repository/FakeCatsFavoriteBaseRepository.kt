package org.catsproject.project.data.database.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flowOf
import org.catsproject.project.data.database.models.CatResulEntity

class FakeCatsFavoriteBaseRepository : CatsFavoriteBaseRepository {

    val favorites = mutableListOf<Pair<String, String>>()
    val favoriteCats = mutableListOf<CatResulEntity>()
    override val favoriteCatsUpdated = MutableSharedFlow<String>(replay = 1)

    override suspend fun addFavoriteByUser(user: String, idCat: String) {
        favorites.add(user to idCat)
        favoriteCatsUpdated.emit(idCat)
    }

    override suspend fun deleteFavoriteByUser(user: String, idCat: String) {
        favorites.remove(user to idCat)
        favoriteCatsUpdated.emit(idCat)
    }

    override suspend fun getListMyCatsFavorite(user: String): Flow<List<CatResulEntity>> {
        return flowOf(favoriteCats)
    }
}
