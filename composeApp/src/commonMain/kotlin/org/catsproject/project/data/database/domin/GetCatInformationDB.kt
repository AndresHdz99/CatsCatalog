package org.catsproject.project.data.database.domin

import org.catsproject.project.core.toDomainInformation
import org.catsproject.project.data.database.repository.CatsDataBaseRepository
import org.catsproject.project.data.database.repository.CatsDataBaseRepositoryImpl
import org.catsproject.project.data.model.CatInformation

class GetCatInformationDB(
    private val catsDataBaseRepository: CatsDataBaseRepository
)
 {
    suspend operator fun invoke(id: String, user: String): CatInformation? {
        return catsDataBaseRepository.getCatById(id, user)?.toDomainInformation()
    }
}