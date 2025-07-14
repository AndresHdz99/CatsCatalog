package org.catsproject.project.data.network.repository

import kotlinx.coroutines.test.runTest
import org.catsproject.project.data.network.datasource.CatsRemoteDataSource
import org.catsproject.project.data.network.networkmodel.CatsModelServicesItem
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CatsCatalogImplTest {


    class FakeCatsRemoteDataSource(
        private val catsToReturn: Result<List<CatsModelServicesItem>>
    ) : CatsRemoteDataSource {
        override suspend fun fetchCats(page: Int): Result<List<CatsModelServicesItem>> {
            return catsToReturn
        }
    }

    @Test
    fun `fetchCats returns expected cats when success`() = runTest {

        val expectedCats = listOf(
            CatsModelServicesItem(
                breeds = emptyList(),
                height = 120,
                id = "cat123",
                url = "https://example.com/cat123.jpg",
                width = 120
            )
        )
        val fakeDataSource = FakeCatsRemoteDataSource(Result.success(expectedCats))
        val repository = CatsCatalogImpl(fakeDataSource)


        val result = repository.fetchCats(1)


        assertTrue(result.isSuccess)
        assertEquals(expectedCats, result.getOrNull())
    }

    @Test
    fun `fetchCats returns failure when data source fails`() = runTest {

        val expectedError = Exception("Network error")
        val fakeDataSource = FakeCatsRemoteDataSource(Result.failure(expectedError))
        val repository = CatsCatalogImpl(fakeDataSource)


        val result = repository.fetchCats(2)


        assertTrue(result.isFailure)
        assertEquals(expectedError, result.exceptionOrNull())
    }
}
