package org.catsproject.project.data.network.datasource


import catscatalog.composeapp.generated.resources.Res
import catscatalog.composeapp.generated.resources.key_api_cats
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.catsproject.project.data.network.networkmodel.CatsModelServicesItem
import org.jetbrains.compose.resources.getString


class CatsRemoteSource(private val client: HttpClient) : CatsRemoteDataSource {

    override suspend fun fetchCats(page: Int): Result<List<CatsModelServicesItem>> {
        val apiKey = getString(Res.string.key_api_cats)
        val url = "https://api.thecatapi.com/v1/images/search?limit=10&page=$page&has_breeds=true&api_key=$apiKey"

        return try {
            val result: List<CatsModelServicesItem> = client.get(url).body()
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
