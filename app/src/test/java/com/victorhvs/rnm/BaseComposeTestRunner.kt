package com.victorhvs.rnm

import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.robolectric.annotation.Config

@Config(instrumentedPackages = ["androidx.loader.content"])
abstract class BaseComposeTestRunner : RobolectricTestRunner() {

    @get:Rule
    val composeTestRule = createComposeRule()
}
