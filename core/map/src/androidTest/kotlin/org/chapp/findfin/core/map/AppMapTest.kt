package org.chapp.findfin.core.map

import androidx.activity.ComponentActivity
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.test.platform.app.InstrumentationRegistry
import com.google.android.gms.maps.MapsInitializer
import io.mockk.mockk
import org.chapp.findfin.core.design.ui.foundation.modifier.contentDescription
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.CountDownLatch

class AppMapTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun setUp() {
        val latch = CountDownLatch(1)
        MapsInitializer.initialize(
            InstrumentationRegistry.getInstrumentation().targetContext,
            MapsInitializer.Renderer.LATEST,
        ) {
            latch.countDown()
        }
        latch.await()
    }

    @Test
    fun testAppMap_init() {
        val mockOnBoundsChange = mockk<((PositionBounds?) -> Unit)>(relaxed = true)

        composeTestRule.setContent {
            AppMap<Any>(
                modifier = Modifier.contentDescription("app_map"),
                initPosition = Position(37.7749, -122.4194),
                initZoom = 10f,
                onBoundsChange = mockOnBoundsChange,
                markerContent = {},
            )
        }

        composeTestRule
            .onNodeWithContentDescription("app_map")
            .assertIsDisplayed()
    }
}
