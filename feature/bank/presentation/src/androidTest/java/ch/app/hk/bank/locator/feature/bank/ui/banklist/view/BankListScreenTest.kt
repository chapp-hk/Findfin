package ch.app.hk.bank.locator.feature.bank.ui.banklist.view

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.every
import io.mockk.mockk
import org.chapp.findfin.core.design.ui.foundation.AppContent
import org.chapp.findfin.core.design.ui.foundation.ScreenState
import org.chapp.findfin.core.design.ui.foundation.mutableScreenStateFlowOf
import org.chapp.findfin.feature.bank.presentation.R
import org.chapp.findfin.feature.bank.presentation.ui.banklist.view.BankListScreen
import org.chapp.findfin.feature.bank.presentation.ui.banklist.viewmodel.BankListViewModelImpl
import org.chapp.findfin.testing.instrument.HiltComponentActivity
import org.chapp.findfin.testing.instrument.getResourceString
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class BankListScreenTest {
    @get:Rule(order = 0)
    val hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltComponentActivity>()

    @BindValue
    val bankListViewModel = mockk<BankListViewModelImpl>(relaxed = true)

    @Before
    fun setup() {
        hiltTestRule.inject()
    }

    @Test
    fun testBankListScreenShouldShowTitle() {
        every { bankListViewModel.screenState } returns
            mutableScreenStateFlowOf(ScreenState.Success(emptyList()))

        composeTestRule.setContent {
            AppContent {
                BankListScreen()
            }
        }

        composeTestRule
            .onNodeWithText(getResourceString(R.string.bank_title_bank_list))
            .assertIsDisplayed()
    }

    @Test
    fun testBankListScreenShouldShowBankList() {
        every { bankListViewModel.screenState } returns
            mutableScreenStateFlowOf(ScreenState.Success(listOf("Bank 1", "Bank 2")))

        composeTestRule.setContent {
            AppContent {
                BankListScreen()
            }
        }

        composeTestRule
            .onAllNodesWithContentDescription(getResourceString(R.string.bank_content_description_bank_item))
            .assertCountEquals(2)
    }
}
