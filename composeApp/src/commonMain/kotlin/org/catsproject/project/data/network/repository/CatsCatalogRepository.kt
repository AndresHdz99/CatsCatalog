package org.catsproject.project.data.network.repository

import org.catsproject.project.data.network.networkmodel.CatsModelServicesItem

interface CatsCatalogRepository {
    suspend fun fetchCats(page:Int):Result<List<CatsModelServicesItem>>
}