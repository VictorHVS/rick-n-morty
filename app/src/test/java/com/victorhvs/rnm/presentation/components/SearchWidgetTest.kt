package com.victorhvs.rnm.presentation.components

import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performTextInput
import com.victorhvs.rnm.BaseComposeTestRunner
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class SearchWidgetTest : BaseComposeTestRunner() {

    @Test
    fun searchWidget_hasCorrectTestTag() {
        composeTestRule.setContent {
            SearchWidget(text = "", onTextChange = {}, onSearchClicked = {})
        }

        composeTestRule.onNodeWithTag("SearchWidget").assertExists()
    }

    @Test
    fun searchWidget_textInputUpdatesCorrectly() {
        var searchText = ""

        composeTestRule.setContent {
            SearchWidget(text = searchText, onTextChange = { searchText = it }, onSearchClicked = {})
        }

        val inputText = "Test"
        composeTestRule.onNodeWithTag("SearchWidget").performTextInput(inputText)
        assertEquals(inputText, searchText)
    }

    @Test
    fun searchWidget_onSearchClickedTriggered() {
        var searchClicked = false
        val searchText = "Test"

        composeTestRule.setContent {
            SearchWidget(text = searchText, onTextChange = {}, onSearchClicked = { searchClicked = true })
        }

        composeTestRule.onNodeWithTag("SearchWidget").performImeAction()
        assertTrue(searchClicked)
    }
}
