package org.chapp.findfin.feature.setting.ui.list.runtime

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.kotest.matchers.types.shouldBeInstanceOf
import org.chapp.findfin.testing.instrument.HiltComponentActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class RememberThemePreferenceStoreTest {
    @get:Rule(order = 0)
    val hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltComponentActivity>()

    @Before
    fun setup() {
        hiltTestRule.inject()
    }

    @Test
    fun testRememberThemePreferenceStore() {
        composeTestRule.setContent {
            rememberThemePreferenceStore().shouldBeInstanceOf<ThemePreferenceStore>()
        }
    }
}
