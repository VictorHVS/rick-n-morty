package com.victorhvs.rnm.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
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
            SectionTitle(title = R.string.episodes, action = { clicked = true })
        }

        composeTestRule.onNodeWithText("Action").assertExists().performClick()
        assertTrue(clicked)
    }

    @Test
    fun sectionTitle_fillsMaxWidth() {
        val containerWidth = 200.dp

        composeTestRule.setContent {
            Box(Modifier.size(containerWidth, 100.dp)) {
                SectionTitle(title = R.string.episodes)
            }
        }

        composeTestRule.onNodeWithTag("SectionTitle").assertWidthIsEqualTo(containerWidth)
    }

}
