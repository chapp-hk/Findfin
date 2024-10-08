package org.chapp.findfin.core.map

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import org.junit.Rule
import org.junit.Test

class AppMapTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun testAppMap_initPosition() {
        composeTestRule.setContent {
            AppMap(
                initPosition = Position(22.3193, 114.1694),
                initZoom = 10f,
                onMapLoaded = {
                },
            )
        }
    }
}
