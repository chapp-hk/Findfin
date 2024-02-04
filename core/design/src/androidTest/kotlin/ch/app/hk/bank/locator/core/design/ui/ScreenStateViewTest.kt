package ch.app.hk.bank.locator.core.design.ui

import androidx.compose.material3.Text
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import ch.app.hk.bank.locator.core.design.theme.AppTheme
import org.junit.Rule
import org.junit.Test

class ScreenStateViewTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testEmpty() {
        val state = ScreenState.Empty

        composeTestRule.setContent {
            AppTheme {
                ScreenStateView(
                    state = state,
                    empty = { Text(text = "empty") },
                    success = {},
                )
            }
        }

        composeTestRule
            .onNodeWithText("empty")
            .assertIsDisplayed()
    }

    @Test
    fun testLoading() {
        val state = ScreenState.Loading

        composeTestRule.setContent {
            AppTheme {
                ScreenStateView(
                    state = state,
                    loading = { Text(text = "loading") },
                    success = {},
                )
            }
        }

        composeTestRule
            .onNodeWithText("loading")
            .assertIsDisplayed()
    }

    @Test
    fun testError() {
        val state = ScreenState.Error(cause = Throwable("test-error"), data = "test-data")

        composeTestRule.setContent {
            AppTheme {
                ScreenStateView(
                    state = state,
                    error = { cause, data ->
                        Text(text = "cause: ${cause.message}, data: $data")
                    },
                    success = {},
                )
            }
        }

        composeTestRule
            .onNodeWithText("cause: test-error, data: test-data")
            .assertIsDisplayed()
    }

    @Test
    fun testSuccess() {
        val state = ScreenState.Success("test-success")

        composeTestRule.setContent {
            AppTheme {
                ScreenStateView(
                    state = state,
                    success = { data ->
                        Text(text = data)
                    },
                )
            }
        }

        composeTestRule
            .onNodeWithText("test-success")
            .assertIsDisplayed()
    }
}
