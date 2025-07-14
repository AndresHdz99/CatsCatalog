package org.catsproject.project.data.network.domain

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.catsproject.project.data.database.models.CatResulEntity
import org.catsproject.project.data.network.domain.GetCatsPageDataBase
import kotlin.test.Test


class GetCatsPageDataBaseTest {

    @Test
    fun `invoke returns transformed cats list`() = runTest {

        val fakeRepo = FakeCatsDataBaseRepositoryImpl()
        val dummyCats = listOf(
            CatResulEntity(
                _id = "id1",
                url = "url1",
                name = "Cat One",
                temperament = "Friendly",
                description = "desc",
                energyLevel = 5,
                intelligence = 4,
                page = 1,
                favorite = true,
                origin = "origin",
                life_span = "12 years"
            ),
            CatResulEntity(
                _id = "id2",
                url = "url2",
                name = "Cat Two",
                temperament = "Playful",
                description = "desc2",
                energyLevel = 6,
                intelligence = 7,
                page = 1,
                favorite = false,
                origin = "origin2",
                life_span = "10 years"
            )
        )
        fakeRepo.catsToReturn = dummyCats

        val useCase = GetCatsPageDataBase(fakeRepo)


        val resultList = useCase.invoke(page = 1, user = "user1").toList()


        assertEquals(1, resultList.size)
        val cats = resultList[0]
        assertEquals(2, cats.size)
        assertEquals("id1", cats[0].id)
        assertEquals("Cat One", cats[0].name)
        assertEquals("Friendly", cats[0].temperament)
        assertEquals(true, cats[0].favorite)

        assertEquals("id2", cats[1].id)
        assertEquals("Cat Two", cats[1].name)
        assertEquals("Playful", cats[1].temperament)
        assertEquals(false, cats[1].favorite)

        assertEquals("user1", fakeRepo.emittedUser)
        assertEquals(1, fakeRepo.emittedPage)
    }
}
