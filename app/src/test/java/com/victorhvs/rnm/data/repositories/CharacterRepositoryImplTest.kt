package com.victorhvs.rnm.data.repositories

import androidx.paging.PagingData
import com.victorhvs.rnm.data.datasources.remote.RNMService
import com.victorhvs.rnm.data.models.Character
import com.victorhvs.rnm.data.models.Location
import com.victorhvs.rnm.data.models.Origin
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.HttpException
import retrofit2.Response

@ExperimentalCoroutinesApi
class CharacterRepositoryImplTest {

    private lateinit var repository: CharacterRepositoryImpl
    private lateinit var rnmService: RNMService

    @Before
    fun setUp() {
        rnmService = mock()
        repository = CharacterRepositoryImpl(rnmService)
    }

    @Test
    fun `searchCharacter returns a Flow of PagingData`() = runTest {
        val query = "Rick"
        // We are not testing the PagingSource itself here, just that the repository returns a Pager
        // The actual content of PagingData would come from the PagingSource interactions
        val characterFlow = repository.searchCharacter(query)
        assertNotNull(characterFlow)
        // We can collect once to ensure it's a valid flow, though it will be empty PagingData
        // as the PagingSource is not mocked to return actual data in this unit test.
        val pagingData = characterFlow.first() // Should emit PagingData.empty() by default from a new Pager
        assertNotNull(pagingData)
    }

    @Test
    fun `getCharacter success returns character`() = runTest {
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
        whenever(rnmService.getCharacter(charId)).thenReturn(fakeCharacter)

        val result = repository.getCharacter(charId)

        assertEquals(fakeCharacter, result)
    }

    @Test(expected = HttpException::class)
    fun `getCharacter failure throws HttpException`() = runTest {
        val charId = 1
        val httpException = HttpException(Response.error<Any>(404, mock()))
        whenever(rnmService.getCharacter(charId)).thenThrow(httpException)

        repository.getCharacter(charId) // This should throw HttpException
    }

    @Test
    fun `getCharacters (plural) success returns list of characters`() = runTest {
        val charIds = listOf(1, 2)
        val fakeCharacter1 = Character(id = 1, name = "Rick", status = "", species = "", type = "", gender = "", origin = mock(), location = mock(), image = "", episode = listOf(), url = "", created = "")
        val fakeCharacter2 = Character(id = 2, name = "Morty", status = "", species = "", type = "", gender = "", origin = mock(), location = mock(), image = "", episode = listOf(), url = "", created = "")
        val fakeCharacters = listOf(fakeCharacter1, fakeCharacter2)

        whenever(rnmService.getCharacters(charIds.joinToString(","))).thenReturn(fakeCharacters)

        val result = repository.getCharacters(charIds)
        assertEquals(fakeCharacters, result)
    }

    @Test(expected = HttpException::class)
    fun `getCharacters (plural) failure throws HttpException`() = runTest {
        val charIds = listOf(1, 2)
        val httpException = HttpException(Response.error<Any>(404, mock()))
        whenever(rnmService.getCharacters(charIds.joinToString(","))).thenThrow(httpException)

        repository.getCharacters(charIds)
    }

     @Test
    fun `getCharacters (plural) with empty list returns empty list`() = runTest {
        val charIds = emptyList<Int>()
        // No need to mock rnmService as it shouldn't be called for an empty list.
        // However, if the implementation *does* call it with an empty string,
        // you might need: whenever(rnmService.getCharacters("")).thenReturn(emptyList())

        val result = repository.getCharacters(charIds)
        assertEquals(emptyList<Character>(), result)
    }
}
