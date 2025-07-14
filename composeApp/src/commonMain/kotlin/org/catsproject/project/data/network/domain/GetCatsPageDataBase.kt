package org.catsproject.project.data.network.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.catsproject.project.core.toDomain
import org.catsproject.project.data.database.repository.CatsDataBaseRepository
import org.catsproject.project.data.database.repository.CatsDataBaseRepositoryImpl
import org.catsproject.project.data.model.CatsImage

class GetCatsPageDataBase (
    private val catsDataBaseRepositoryImpl: CatsDataBaseRepository
){

    suspend operator fun invoke(page: Int,user:String): Flow<List<CatsImage>> {
        return catsDataBaseRepositoryImpl.getListCatsCatalog(user, page)
            .map { catList -> catList.map { cat -> cat.toDomain() } }
    }

}