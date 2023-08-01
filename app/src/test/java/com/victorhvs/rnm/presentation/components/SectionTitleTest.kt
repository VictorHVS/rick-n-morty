package com.victorhvs.rnm.presentation.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.victorhvs.rnm.BaseComposeTestRunner
import com.victorhvs.rnm.R
import org.junit.Assert.assertTrue
import org.junit.Test

class SectionTitleTest : BaseComposeTestRunner() {

    @Test
    fun sectionTitle_displaysCorrectText() {
        val testTitle = R.string.episodes

        composeTestRule.setContent {
            SectionTitle(title = testTitle)
        }

        composeTestRule.onNodeWithText("Episodes").assertExists()
    }

    @Test
    fun sectionTitle_hasCorrectTestTag() {
        composeTestRule.setContent {
            SectionTitle(title = R.string.episodes)
        }

        composeTestRule.onNodeWithTag("SectionTitle").assertExists()
    }

    @Test
    fun sectionTitle_actionButtonDisplayedAndClickable() {
        var clicked = false

        composeTestRule.setContent {
            SectionTitle(title = R.string.episodes) {
                Button(onClick = { clicked = true }) {
                    Text("Action")
                }
            }
        }

        composeTestRule.onNodeWithText("Action").assertExists().performClick()
        assertTrue(clicked)
    }

}
