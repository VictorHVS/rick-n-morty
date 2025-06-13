package com.victorhvs.rnm.presentation.screens.detail

import com.victorhvs.rnm.core.DispatcherProvider
import com.victorhvs.rnm.data.models.Character
import com.victorhvs.rnm.data.models.Episode
import com.victorhvs.rnm.data.models.Location
import com.victorhvs.rnm.data.models.Origin
import com.victorhvs.rnm.data.repositories.CharacterRepository
import com.victorhvs.rnm.data.repositories.EpisodesRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class DetailViewModelTest {

    private lateinit var viewModel: DetailViewModel
    private lateinit var charRepository: CharacterRepository
    private lateinit var epiRepository: EpisodesRepository
    private lateinit var dispatcherProvider: DispatcherProvider
    private val testDispatcher = StandardTestDispatcher()

    // Mocked data (can remain as is, or use relaxed mockks if complex object creation is an issue)
    private val fakeCharacter = Character(
        id = 1,
        name = "Rick Sanchez",
        status = "Alive",
        species = "Human",
        type = "",
        gender = "Male",
        origin = Origin(name = "Earth (C-137)", url = "url_origin"),
        location = Location(name = "Citadel of Ricks", url = "url_location"),
        image = "image_url",
        episode = listOf("episode_url/1", "episode_url/2"),
        url = "char_url",
        created = "timestamp"
    )

    private val fakeEpisodes = listOf(
        Episode(id = 1, name = "Pilot", airDate = "December 2, 2013", episode = "S01E01", characters = listOf(), url = "", created = ""),
        Episode(id = 2, name = "Lawnmower Dog", airDate = "December 9, 2013", episode = "S01E02", characters = listOf(), url = "", created = "")
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        charRepository = mockk()
        epiRepository = mockk()
        dispatcherProvider = mockk()

        every { dispatcherProvider.io() } returns testDispatcher
        // Note: No need to mock main() or default() if not explicitly used by viewModel with those specific dispatchers.
        // If they are, they should also be mocked:
        // every { dispatcherProvider.main() } returns testDispatcher
        // every { dispatcherProvider.default() } returns testDispatcher


        viewModel = DetailViewModel(charRepository, epiRepository, dispatcherProvider)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `GIVEN charId and successful repo calls WHEN fetchCharacterAndEpisodes is called THEN state updates to Success with correct data`() = runTest {
        // GIVEN
        val charId = 1
        coEvery { charRepository.getCharacter(charId) } returns fakeCharacter
        coEvery { epiRepository.getEpisodes(listOf(1, 2)) } returns fakeEpisodes

        // WHEN
        viewModel.fetchCharacterAndEpisodes(charId)
        testDispatcher.scheduler.advanceUntilIdle() // Allow coroutines to complete

        // THEN
        val state = viewModel.state.first()
        assertTrue(state is DetailViewModel.UiState.Success)
        assertEquals(fakeCharacter, (state as DetailViewModel.UiState.Success).charInfo)
        assertEquals(fakeEpisodes, state.episodes)
    }

    @Test
    fun `GIVEN charId and charRepository failure WHEN fetchCharacterAndEpisodes is called THEN state updates to Error`() = runTest {
        // GIVEN
        val charId = 1
        val exception = RuntimeException("Character fetch failed")
        coEvery { charRepository.getCharacter(charId) } throws exception

        // WHEN
        viewModel.fetchCharacterAndEpisodes(charId)
        testDispatcher.scheduler.advanceUntilIdle()

        // THEN
        val state = viewModel.state.first()
        assertTrue(state is DetailViewModel.UiState.Error)
        assertEquals(exception, (state as DetailViewModel.UiState.Error).e)
    }

    @Test
    fun `GIVEN charId, successful charRepository call, and epiRepository failure WHEN fetchCharacterAndEpisodes is called THEN state updates to Error`() = runTest {
        // GIVEN
        val charId = 1
        val exception = RuntimeException("Episodes fetch failed")
        coEvery { charRepository.getCharacter(charId) } returns fakeCharacter
        coEvery { epiRepository.getEpisodes(listOf(1, 2)) } throws exception

        // WHEN
        viewModel.fetchCharacterAndEpisodes(charId)
        testDispatcher.scheduler.advanceUntilIdle()

        // THEN
        val state = viewModel.state.first()
        assertTrue(state is DetailViewModel.UiState.Error)
        assertEquals(exception, (state as DetailViewModel.UiState.Error).e)
    }
}
