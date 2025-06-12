package com.victorhvs.rnm.presentation.screens.detail

import com.victorhvs.rnm.core.DispatcherProvider
import com.victorhvs.rnm.data.models.Character
import com.victorhvs.rnm.data.models.Episode
import com.victorhvs.rnm.data.models.Location
import com.victorhvs.rnm.data.models.Origin
import com.victorhvs.rnm.data.repositories.CharacterRepository
import com.victorhvs.rnm.data.repositories.EpisodesRepository
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
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class DetailViewModelTest {

    private lateinit var viewModel: DetailViewModel
    private lateinit var charRepository: CharacterRepository
    private lateinit var epiRepository: EpisodesRepository
    private lateinit var dispatcherProvider: DispatcherProvider
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        charRepository = mock()
        epiRepository = mock()
        dispatcherProvider = mock()

        // Mock dispatcherProvider to return the testDispatcher for all contexts
        whenever(dispatcherProvider.io()).thenReturn(testDispatcher)
        whenever(dispatcherProvider.main()).thenReturn(testDispatcher)
        whenever(dispatcherProvider.default()).thenReturn(testDispatcher)

        viewModel = DetailViewModel(charRepository, epiRepository, dispatcherProvider)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

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

    @Test
    fun `fetchCharacterAndEpisodes success updates state to Success`() = runTest {
        val charId = 1
        whenever(charRepository.getCharacter(charId)).thenReturn(fakeCharacter)
        whenever(epiRepository.getEpisodes(listOf(1, 2))).thenReturn(fakeEpisodes)

        viewModel.fetchCharacterAndEpisodes(charId)
        // Advance the dispatcher to allow coroutines to complete
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.first()
        assertTrue(state is DetailViewModel.UiState.Success)
        assertEquals(fakeCharacter, (state as DetailViewModel.UiState.Success).charInfo)
        assertEquals(fakeEpisodes, state.episodes)
    }

    @Test
    fun `fetchCharacterAndEpisodes when charRepository fails updates state to Error`() = runTest {
        val charId = 1
        val exception = RuntimeException("Character fetch failed")
        whenever(charRepository.getCharacter(charId)).thenThrow(exception)

        viewModel.fetchCharacterAndEpisodes(charId)
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.first()
        assertTrue(state is DetailViewModel.UiState.Error)
        assertEquals(exception, (state as DetailViewModel.UiState.Error).e)
    }

    @Test
    fun `fetchCharacterAndEpisodes when epiRepository fails updates state to Error`() = runTest {
        val charId = 1
        val exception = RuntimeException("Episodes fetch failed")
        whenever(charRepository.getCharacter(charId)).thenReturn(fakeCharacter)
        whenever(epiRepository.getEpisodes(listOf(1, 2))).thenThrow(exception)

        viewModel.fetchCharacterAndEpisodes(charId)
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.first()
        assertTrue(state is DetailViewModel.UiState.Error)
        assertEquals(exception, (state as DetailViewModel.UiState.Error).e)
    }
}
