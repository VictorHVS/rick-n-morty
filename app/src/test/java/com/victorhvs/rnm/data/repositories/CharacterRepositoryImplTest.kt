package com.victorhvs.rnm.data.repositories

import androidx.paging.PagingData
import com.victorhvs.rnm.data.datasources.remote.RNMService
import com.victorhvs.rnm.data.models.Character
import com.victorhvs.rnm.data.models.Location
import com.victorhvs.rnm.data.models.Origin
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

@ExperimentalCoroutinesApi
class CharacterRepositoryImplTest {

    private lateinit var repository: CharacterRepositoryImpl
    private lateinit var rnmService: RNMService

    @Before
    fun setUp() {
        rnmService = mockk()
        repository = CharacterRepositoryImpl(rnmService)
    }

    @Test
    fun `GIVEN a query WHEN searchCharacter is called THEN a Flow of PagingData is returned`() = runTest {
        // GIVEN
        val query = "Rick"

        // WHEN
        // The actual PagingSource isn't tested here, just that the repo constructs and returns a Pager's flow.
        val characterFlow = repository.searchCharacter(query)

        // THEN
        assertNotNull(characterFlow)
        // Collect once to ensure it's a valid flow. It will likely be PagingData.empty()
        // as the PagingSource itself isn't mocked to provide data in this unit test.
        val pagingData = characterFlow.first()
        assertNotNull(pagingData)
    }

    @Test
    fun `GIVEN a character ID and successful service call WHEN getCharacter is called THEN the character is returned`() = runTest {
        // GIVEN
        val charId = 1
        val fakeCharacter = Character(
            id = charId,
            name = "Rick Sanchez",
            status = "Alive",
            species = "Human",
            type = "",
            gender = "Male",
            origin = Origin(name = "Earth (C-137)", url = "url_origin"),
            location = Location(name = "Citadel of Ricks", url = "url_location"),
            image = "image_url",
            episode = listOf("episode_url/1"),
            url = "char_url",
            created = "timestamp"
        )
        coEvery { rnmService.getCharacter(charId) } returns fakeCharacter

        // WHEN
        val result = repository.getCharacter(charId)

        // THEN
        assertEquals(fakeCharacter, result)
    }

    @Test(expected = HttpException::class)
    fun `GIVEN a character ID and service failure WHEN getCharacter is called THEN HttpException is thrown`() = runTest {
        // GIVEN
        val charId = 1
        val responseError = mockk<Response<Any>>(relaxed = true) // relaxed mock for Response.error
        val httpException = HttpException(responseError)
        // It's often simpler to directly mock the exception if its internal state isn't critical for the test
        // val httpException = mockk<HttpException>()
        coEvery { rnmService.getCharacter(charId) } throws httpException

        // WHEN
        repository.getCharacter(charId)

        // THEN
        // Expected HttpException is declared in @Test annotation
    }

    @Test
    fun `GIVEN character IDs and successful service call WHEN getCharacters is called THEN list of characters is returned`() = runTest {
        // GIVEN
        val charIds = listOf(1, 2)
        val fakeCharacter1 = mockk<Character>() // Relaxed mocks if details aren't crucial
        val fakeCharacter2 = mockk<Character>()
        val fakeCharacters = listOf(fakeCharacter1, fakeCharacter2)
        val idsString = charIds.joinToString(",")

        coEvery { rnmService.getCharacters(idsString) } returns fakeCharacters

        // WHEN
        val result = repository.getCharacters(charIds)

        // THEN
        assertEquals(fakeCharacters, result)
    }

    @Test(expected = HttpException::class)
    fun `GIVEN character IDs and service failure WHEN getCharacters is called THEN HttpException is thrown`() = runTest {
        // GIVEN
        val charIds = listOf(1, 2)
        val idsString = charIds.joinToString(",")
        val responseError = mockk<Response<Any>>(relaxed = true)
        val httpException = HttpException(responseError)
        // val httpException = mockk<HttpException>()
        coEvery { rnmService.getCharacters(idsString) } throws httpException

        // WHEN
        repository.getCharacters(charIds)

        // THEN
        // Expected HttpException
    }

    @Test
    fun `GIVEN an empty list of character IDs WHEN getCharacters is called THEN an empty list is returned`() = runTest {
        // GIVEN
        val charIds = emptyList<Int>()

        // WHEN
        // No service call should be made, so no coEvery needed for rnmService.
        val result = repository.getCharacters(charIds)

        // THEN
        assertEquals(emptyList<Character>(), result)
    }
}
