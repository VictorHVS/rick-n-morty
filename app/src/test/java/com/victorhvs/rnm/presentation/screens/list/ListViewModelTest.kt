package com.victorhvs.rnm.presentation.screens.list

import androidx.paging.PagingData
import com.victorhvs.rnm.data.models.Character
import com.victorhvs.rnm.data.repositories.CharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class ListViewModelTest {

    private lateinit var viewModel: ListViewModel
    private lateinit var repository: CharacterRepository
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = mock()
        viewModel = ListViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `updateSearchQuery updates searchQuery state flow`() = runTest {
        val query = "Rick"
        viewModel.updateSearchQuery(query)
        assertEquals(query, viewModel.searchQuery.first())
    }

    @Test
    fun `searchCharacter calls repository and updates searchedCharacter flow`() = runTest {
        val query = "Morty"
        val character = Character(id = 1, name = "Morty", status = "Alive", species = "Human", type = "", gender = "Male", origin = mock(), location = mock(), image = "", episode = listOf(), url = "", created = "")
        val pagingData = PagingData.from(listOf(character))
        val flowPagingData = flowOf(pagingData)

        whenever(repository.searchCharacter(query)).thenReturn(flowPagingData)

        // Trigger collection of searchQuery to initiate searchCharacter
        viewModel.updateSearchQuery(query)
        testDispatcher.scheduler.advanceUntilIdle() // Ensure debounce and collectors are run

        // Verify repository interaction
        verify(repository).searchCharacter(query)

        // Verify state update
        assertEquals(pagingData, viewModel.searchedCharacter.first())
    }

    @Test
    fun `searchCharacter with empty query updates flow with empty PagingData`() = runTest {
        val query = ""
        val emptyPagingData = PagingData.empty<Character>()
        val flowEmptyPagingData = flowOf(emptyPagingData)

        whenever(repository.searchCharacter(query)).thenReturn(flowEmptyPagingData)

        viewModel.updateSearchQuery(query)
        testDispatcher.scheduler.advanceUntilIdle()


        verify(repository).searchCharacter(query)
        assertEquals(emptyPagingData, viewModel.searchedCharacter.first())
    }
}
