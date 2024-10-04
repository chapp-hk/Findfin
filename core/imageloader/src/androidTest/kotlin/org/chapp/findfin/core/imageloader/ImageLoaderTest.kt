package org.chapp.findfin.core.imageloader

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import org.chapp.findfin.core.imageloader.test.R
import org.junit.Rule
import org.junit.Test

class ImageLoaderTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun testImageView() {
        composeTestRule.setContent {
            ImageView(
                source = R.drawable.core_imageloader_error,
                contentDescription = "test image loader",
            )
        }

        composeTestRule
            .onNodeWithContentDescription("test image loader")
            .assertIsDisplayed()
    }
}
