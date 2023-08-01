package com.victorhvs.rnm.presentation.components

import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.unit.dp
import com.victorhvs.rnm.BaseComposeTestRunner
import org.junit.Test

class CharRoundedTest : BaseComposeTestRunner() {

    @Test
    fun charRounded_displaysCorrectText() {
        val testText = "A"

        composeTestRule.setContent {
            CharRounded(text = testText)
        }

        composeTestRule.onNodeWithText(testText).assertExists()
    }

    @Test
    fun charRounded_hasCorrectTestTag() {
        composeTestRule.setContent {
            CharRounded(text = "A")
        }

        composeTestRule.onNodeWithTag("CharRounded").assertExists()
    }

    @Test
    fun charRounded_hasCorrectSize() {
        composeTestRule.setContent {
            CharRounded(text = "A")
        }

        composeTestRule.onNodeWithTag("CharRounded").assertWidthIsEqualTo(40.dp)
            .assertHeightIsEqualTo(40.dp)
    }
}
