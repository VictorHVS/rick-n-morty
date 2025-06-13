package com.victorhvs.rnm.data.repositories

import com.victorhvs.rnm.data.datasources.remote.RNMService
import com.victorhvs.rnm.data.models.Episode
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

@ExperimentalCoroutinesApi
class EpisodesRepositoryImplTest {

    private lateinit var repository: EpisodesRepositoryImpl
    private lateinit var rnmService: RNMService

    // Using mockk for fake data to simplify if specific values aren't critical for a test
    private val fakeEpisode1: Episode = mockk(relaxed = true)
    private val fakeEpisode2: Episode = mockk(relaxed = true)

    @Before
    fun setUp() {
        rnmService = mockk()
        repository = EpisodesRepositoryImpl(rnmService)
    }

    @Test
    fun `GIVEN multiple episode IDs and successful service call WHEN getEpisodes is called THEN list of episodes is returned`() = runTest {
        // GIVEN
        val episodeIds = listOf(1, 2)
        val idsString = episodeIds.joinToString(",")
        val fakeEpisodesList = listOf(fakeEpisode1, fakeEpisode2)

        coEvery { rnmService.getEpisodes(idsString) } returns fakeEpisodesList

        // WHEN
        val result = repository.getEpisodes(episodeIds)

        // THEN
        assertEquals(fakeEpisodesList, result)
    }

    @Test
    fun `GIVEN single episode ID and successful service call WHEN getEpisodes is called THEN list with one episode is returned`() = runTest {
        // GIVEN
        val episodeIds = listOf(1)
        val idsString = "1"
        val fakeEpisodesList = listOf(fakeEpisode1) // Service expected to return a list

        coEvery { rnmService.getEpisodes(idsString) } returns fakeEpisodesList

        // WHEN
        val result = repository.getEpisodes(episodeIds)

        // THEN
        assertEquals(fakeEpisodesList, result)
    }

    @Test
    fun `GIVEN single episode ID and successful service call WHEN getEpisode (singular) is called THEN single episode is returned`() = runTest {
        // GIVEN
        val episodeId = 1
        coEvery { rnmService.getEpisode(episodeId) } returns fakeEpisode1

        // WHEN
        val result = repository.getEpisode(episodeId) // Assumes EpisodesRepository has getEpisode(id: Int)

        // THEN
        assertEquals(fakeEpisode1, result)
    }

    @Test(expected = HttpException::class)
    fun `GIVEN episode IDs and service failure WHEN getEpisodes is called THEN HttpException is thrown`() = runTest {
        // GIVEN
        val episodeIds = listOf(1, 2)
        val idsString = episodeIds.joinToString(",")
        val responseError = mockk<Response<Any>>(relaxed = true)
        val httpException = HttpException(responseError)
        // val httpException = mockk<HttpException>()
        coEvery { rnmService.getEpisodes(idsString) } throws httpException

        // WHEN
        repository.getEpisodes(episodeIds)

        // THEN
        // Expected HttpException
    }

    @Test(expected = HttpException::class)
    fun `GIVEN episode ID and service failure WHEN getEpisode (singular) is called THEN HttpException is thrown`() = runTest {
        // GIVEN
        val episodeId = 1
        val responseError = mockk<Response<Any>>(relaxed = true)
        val httpException = HttpException(responseError)
        // val httpException = mockk<HttpException>()
        coEvery { rnmService.getEpisode(episodeId) } throws httpException

        // WHEN
        repository.getEpisode(episodeId)

        // THEN
        // Expected HttpException
    }

    @Test
    fun `GIVEN an empty list of episode IDs WHEN getEpisodes is called THEN an empty list is returned`() = runTest {
        // GIVEN
        val episodeIds = emptyList<Int>()

        // WHEN
        // No service call expected, so no coEvery for rnmService.
        val result = repository.getEpisodes(episodeIds)

        // THEN
        assertTrue(result.isEmpty())
    }
}
