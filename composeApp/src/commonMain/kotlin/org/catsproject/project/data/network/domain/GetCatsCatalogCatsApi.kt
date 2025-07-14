package org.catsproject.project.data.network.domain

import org.catsproject.project.core.toDomain
import org.catsproject.project.core.toServiceByDB
import org.catsproject.project.data.database.repository.CatsDataBaseRepository
import org.catsproject.project.data.database.repository.CatsDataBaseRepositoryImpl
import org.catsproject.project.data.model.CatsImage
import org.catsproject.project.data.network.repository.CatsCatalogImpl
import org.catsproject.project.data.network.repository.CatsCatalogRepository

class GetCatsCatalogCatsApi(
    private val catsCatalogImpl: CatsCatalogRepository,
    private val catsDataBaseRepositoryImpl: CatsDataBaseRepository
) {

    suspend operator fun invoke(page: Int): Result<Unit> {
        return catsCatalogImpl.fetchCats(page).mapCatching { cats ->
            if (cats.isNotEmpty()) {
                val list = cats
                    .filter { it.breeds.isNotEmpty() }
                    .map { it.toServiceByDB(page) }

                catsDataBaseRepositoryImpl.deleteCatsPage(page)
                catsDataBaseRepositoryImpl.saveCats(list)
            }
        }
    }

}