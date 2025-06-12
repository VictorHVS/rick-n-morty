package com.victorhvs.rnm.data.repositories

import com.victorhvs.rnm.data.datasources.remote.RNMService
import com.victorhvs.rnm.data.models.Episode
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.HttpException
import retrofit2.Response

@ExperimentalCoroutinesApi
class EpisodesRepositoryImplTest {

    private lateinit var repository: EpisodesRepositoryImpl
    private lateinit var rnmService: RNMService

    @Before
    fun setUp() {
        rnmService = mock()
        repository = EpisodesRepositoryImpl(rnmService)
    }

    private val fakeEpisode1 = Episode(id = 1, name = "Pilot", airDate = "S01E01", episode = "S01E01", characters = listOf(), url = "", created = "")
    private val fakeEpisode2 = Episode(id = 2, name = "Lawnmower Dog", airDate = "S01E02", episode = "S01E02", characters = listOf(), url = "", created = "")

    @Test
    fun `getEpisodes with multiple IDs success returns list of episodes`() = runTest {
        val episodeIds = listOf(1, 2)
        val idsString = episodeIds.joinToString(",")
        val fakeEpisodesList = listOf(fakeEpisode1, fakeEpisode2)

        whenever(rnmService.getEpisodes(idsString)).thenReturn(fakeEpisodesList)

        val result = repository.getEpisodes(episodeIds)

        assertEquals(fakeEpisodesList, result)
    }

    @Test
    fun `getEpisodes with single ID success returns list containing one episode`() = runTest {
        val episodeIds = listOf(1)
        val idsString = episodeIds.joinToString(",") // "1"
        // Assuming getEpisodes service endpoint always returns a list, even for a single ID query
        val fakeEpisodesList = listOf(fakeEpisode1)

        whenever(rnmService.getEpisodes(idsString)).thenReturn(fakeEpisodesList)

        val result = repository.getEpisodes(episodeIds)

        assertEquals(fakeEpisodesList, result)
    }

    @Test
    fun `getEpisode (singular) success returns single episode`() = runTest {
        val episodeId = 1
        // This test assumes there's a singular getEpisode method in the service if the repo uses it.
        // Based on CharacterRepository, it's likely the service has getEpisode(id) and getEpisodes(idsString)
        // The repository's getEpisode(id) would call rnmService.getEpisode(id)
        whenever(rnmService.getEpisode(episodeId)).thenReturn(fakeEpisode1)

        val result = repository.getEpisode(episodeId) // Assuming EpisodesRepository has getEpisode(id: Int)

        assertEquals(fakeEpisode1, result)
    }


    @Test(expected = HttpException::class)
    fun `getEpisodes failure throws HttpException`() = runTest {
        val episodeIds = listOf(1, 2)
        val idsString = episodeIds.joinToString(",")
        val httpException = HttpException(Response.error<Any>(404, mock()))

        whenever(rnmService.getEpisodes(idsString)).thenThrow(httpException)

        repository.getEpisodes(episodeIds) // This should throw
    }

    @Test(expected = HttpException::class)
    fun `getEpisode (singular) failure throws HttpException`() = runTest {
        val episodeId = 1
        val httpException = HttpException(Response.error<Any>(404, mock()))

        whenever(rnmService.getEpisode(episodeId)).thenThrow(httpException)

        repository.getEpisode(episodeId) // This should throw
    }

    @Test
    fun `getEpisodes with empty list returns empty list`() = runTest {
        val episodeIds = emptyList<Int>()
        // No network call should be made for an empty list of IDs.
        val result = repository.getEpisodes(episodeIds)
        assertTrue(result.isEmpty())
    }
}
