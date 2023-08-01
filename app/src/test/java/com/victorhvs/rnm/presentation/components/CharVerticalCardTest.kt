package com.victorhvs.rnm.presentation.components

import androidx.compose.ui.test.assertWidthIsAtLeast
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import com.victorhvs.rnm.BaseComposeTestRunner
import org.junit.Assert.assertTrue
import org.junit.Test

class CharVerticalCardTest : BaseComposeTestRunner() {

    @Test
    fun charVerticalCard_displaysCorrectText() {
        val testName = "Character Name"
        val testSpecies = "Character Species"

        composeTestRule.setContent {
            CharVerticalCard(imageUrl = "", name = testName, species = testSpecies)
        }

        composeTestRule.onNodeWithText(testName).assertExists()
        composeTestRule.onNodeWithText(testSpecies).assertExists()
    }

    @Test
    fun charVerticalCard_hasCorrectTestTag() {
        composeTestRule.setContent {
            CharVerticalCard(imageUrl = "", name = "Name", species = "Species")
        }

        composeTestRule.onNodeWithTag("CharVerticalCard").assertExists()
    }

    @Test
    fun charVerticalCard_hasCorrectWidthConstraints() {
        composeTestRule.setContent {
            CharVerticalCard(imageUrl = "", name = "Name", species = "Species")
        }

        composeTestRule.onNodeWithTag("CharVerticalCard")
            .assertWidthIsAtLeast(120.dp)
    }

    @Test
    fun charVerticalCard_onClickActionTriggered() {
        var clicked = false

        composeTestRule.setContent {
            CharVerticalCard(imageUrl = "", name = "Name", species = "Species") {
                clicked = true
            }
        }

        composeTestRule.onNodeWithTag("CharVerticalCard").performClick()
        assertTrue(clicked)
    }

}
