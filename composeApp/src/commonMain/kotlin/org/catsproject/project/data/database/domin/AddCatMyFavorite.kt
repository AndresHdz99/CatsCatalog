package org.catsproject.project.data.database.domin

import org.catsproject.project.data.database.repository.CatsFavoriteBaseRepository
import org.catsproject.project.data.database.repository.CatsFavoriteBaseRepositoryImpl


class AddCatMyFavorite(
   private val catsFavoriteBaseRepositoryImpl: CatsFavoriteBaseRepository
) {

    suspend operator fun invoke(user:String,idCat:String){
        catsFavoriteBaseRepositoryImpl.addFavoriteByUser(user,idCat)
    }

}