package ch.app.hk.bank.locator.feature.setting.ui.list.view

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import ch.app.hk.bank.locator.feature.setting.ui.R
import ch.app.hk.bank.locator.testing.instrument.HiltComponentActivity
import ch.app.hk.bank.locator.testing.instrument.getResourceString
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class SettingListScreenTest {
    @get:Rule(order = 0)
    val hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltComponentActivity>()

    @Before
    fun setup() {
        hiltTestRule.inject()
    }

    @Test
    fun testSettingListScreenDisplaysTitle() {
        composeTestRule.setContent {
            SettingListScreen()
        }

        composeTestRule
            .onNodeWithText(getResourceString(R.string.setting_tab_setting))
            .assertIsDisplayed()
    }
}
