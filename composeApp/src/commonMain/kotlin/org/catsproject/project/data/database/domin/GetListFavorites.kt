package org.catsproject.project.data.database.domin

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.catsproject.project.core.toDomain
import org.catsproject.project.data.database.repository.CatsFavoriteBaseRepository
import org.catsproject.project.data.database.repository.CatsFavoriteBaseRepositoryImpl
import org.catsproject.project.data.model.CatsImage


class GetListFavorites(
    private val catsFavoriteBaseRepositoryImpl: CatsFavoriteBaseRepository
) {

    suspend operator fun invoke(user:String): Flow<List<CatsImage>> {
        return catsFavoriteBaseRepositoryImpl.getListMyCatsFavorite(user).map { catList -> catList.map { cat -> cat.toDomain() } }
    }

}