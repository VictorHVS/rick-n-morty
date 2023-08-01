package com.victorhvs.rnm.presentation.components

import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.victorhvs.rnm.BaseComposeTestRunner
import org.junit.Test

class EpisodeHorizontalCardTest : BaseComposeTestRunner() {
    @Test
    fun episodeHorizontalCard_displaysCorrectText() {
        val testName = "Episode Name"
        val testEpisode = "Episode 1"
        val testAirDate = "2022-01-01"
        val testPosition = 1

        composeTestRule.setContent {
            EpisodeHorizontalCard(
                position = testPosition,
                name = testName,
                episode = testEpisode,
                airDate = testAirDate
            )
        }

        composeTestRule.onNodeWithText(testName).assertExists()
        composeTestRule.onNodeWithText(testEpisode).assertExists()
        composeTestRule.onNodeWithText(testAirDate).assertExists()
        composeTestRule.onNodeWithText(testPosition.toString()).assertExists()
    }

    @Test
    fun episodeHorizontalCard_hasCorrectTestTag() {
        composeTestRule.setContent {
            EpisodeHorizontalCard(
                position = 1,
                name = "Name",
                episode = "Episode",
                airDate = "2022-01-01"
            )
        }

        composeTestRule.onNodeWithTag("EpisodeHorizontalCard").assertExists()
    }
}
