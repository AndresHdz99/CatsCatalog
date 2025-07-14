package org.catsproject.project.data.network.datasource

import org.catsproject.project.data.network.networkmodel.CatsModelServicesItem

interface CatsRemoteDataSource {
    suspend fun fetchCats(page:Int):Result<List<CatsModelServicesItem>>
}