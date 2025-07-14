package org.catsproject.project.data.database.domin

import org.catsproject.project.data.database.repository.CatsFavoriteBaseRepository
import org.catsproject.project.data.database.repository.CatsFavoriteBaseRepositoryImpl


class DeleteCatMyFavorite(
    private val catsFavoriteBaseRepository: CatsFavoriteBaseRepository
) {
    suspend operator fun invoke(user:String, idCat:String) {
        catsFavoriteBaseRepository.deleteFavoriteByUser(user, idCat)
    }
}
