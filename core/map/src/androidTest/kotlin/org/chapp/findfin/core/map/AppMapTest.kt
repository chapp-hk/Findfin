package org.chapp.findfin.core.map

import androidx.activity.ComponentActivity
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import io.mockk.mockk
import org.chapp.findfin.core.design.ui.foundation.modifier.contentDescription
import org.junit.Rule
import org.junit.Test

class AppMapTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun testAppMap_init() {
        val mockOnBoundsChange = mockk<((PositionBounds?) -> Unit)>()

        composeTestRule.setContent {
            AppMap(
                modifier = Modifier.contentDescription("app_map"),
                initPosition = Position(37.7749, -122.4194),
                initZoom = 10f,
                onBoundsChange = mockOnBoundsChange,
            )
        }

        composeTestRule
            .onNodeWithContentDescription("app_map")
            .assertIsDisplayed()
    }
}
