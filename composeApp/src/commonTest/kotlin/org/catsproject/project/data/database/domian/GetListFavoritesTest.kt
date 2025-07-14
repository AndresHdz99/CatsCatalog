package org.catsproject.project.data.database.domian

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.catsproject.project.data.model.CatsImage
import kotlin.test.Test
import kotlin.test.assertEquals

class GetListFavoritesTest {

    @Test
    fun `should transform cats entity to domain`() = runTest {

        val fakeCats = listOf(
            FakeCatsEntity(_id = "cat1", name = "Persian", url = "url1"),
            FakeCatsEntity(_id = "cat2", name = "Siamese", url = "url2")
        )


        val result = flowOf(fakeCats).map { catList ->
            catList.map { cat ->
                CatsImage(
                    id = cat._id,
                    url = cat.url,
                    name = cat.name,
                    temperament = cat.temperament,
                    page = cat.page,
                    favorite = true
                )
            }
        }.toList()


        assertEquals(1, result.size)
        assertEquals(2, result[0].size)
        assertEquals("cat1", result[0][0].id)
        assertEquals("Persian", result[0][0].name)
    }

    @Test
    fun `should handle empty list`() = runTest {

        val emptyList = emptyList<FakeCatsEntity>()


        val result = flowOf(emptyList).map { catList ->
            catList.map { cat ->
                CatsImage(
                    id = cat._id,
                    url = cat.url,
                    name = cat.name,
                    temperament = cat.temperament,
                    page = cat.page,
                    favorite = true
                )
            }
        }.toList()


        assertEquals(1, result.size)
        assertEquals(0, result[0].size)
    }
}


data class FakeCatsEntity(
    val _id: String = "",
    val url: String = "",
    val name: String = "",
    val temperament: String = "Friendly",
    val page: Int = 1
)