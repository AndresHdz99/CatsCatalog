package org.catsproject.project.data.network.repository

import org.catsproject.project.data.network.datasource.CatsRemoteDataSource
import org.catsproject.project.data.network.networkmodel.CatsModelServicesItem

class CatsCatalogImpl(
    private val catsRemoteDataSource: CatsRemoteDataSource
):CatsCatalogRepository {

    override suspend fun fetchCats(page: Int): Result<List<CatsModelServicesItem>> {
        return catsRemoteDataSource.fetchCats(page = page)
    }
}