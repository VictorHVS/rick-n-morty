package com.victorhvs.rnm.presentation.screens.list

import androidx.paging.PagingData
import com.victorhvs.rnm.data.models.Character
import com.victorhvs.rnm.data.repositories.CharacterRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
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

@ExperimentalCoroutinesApi
class ListViewModelTest {

    private lateinit var viewModel: ListViewModel
    private lateinit var repository: CharacterRepository
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk<CharacterRepository>()
        viewModel = ListViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `GIVEN a query WHEN updateSearchQuery is called THEN searchQuery state flow is updated`() = runTest {
        // GIVEN
        val query = "Rick"

        // WHEN
        viewModel.updateSearchQuery(query)

        // THEN
        assertEquals(query, viewModel.searchQuery.first())
    }

    @Test
    fun `GIVEN a query and repository setup WHEN searchCharacter is triggered THEN repository is called and searchedCharacter flow is updated`() = runTest {
        // GIVEN
        val query = "Morty"
        val character = mockk<Character>() // Using mockk for Character as its properties aren't used directly in this test
        val pagingData = PagingData.from(listOf(character))
        val flowPagingData = flowOf(pagingData)

        coEvery { repository.searchCharacter(query) } returns flowPagingData

        // WHEN
        viewModel.updateSearchQuery(query) // This triggers searchCharacter after debounce
        testDispatcher.scheduler.advanceUntilIdle() // Ensure debounce and collectors are run

        // THEN
        coVerify { repository.searchCharacter(query) }
        assertEquals(pagingData, viewModel.searchedCharacter.first())
    }

    @Test
    fun `GIVEN an empty query and repository setup WHEN searchCharacter is triggered THEN repository is called and flow is updated with empty PagingData`() = runTest {
        // GIVEN
        val query = ""
        val emptyPagingData = PagingData.empty<Character>()
        val flowEmptyPagingData = flowOf(emptyPagingData)

        coEvery { repository.searchCharacter(query) } returns flowEmptyPagingData

        // WHEN
        viewModel.updateSearchQuery(query)
        testDispatcher.scheduler.advanceUntilIdle()

        // THEN
        coVerify { repository.searchCharacter(query) }
        assertEquals(emptyPagingData, viewModel.searchedCharacter.first())
    }
}
