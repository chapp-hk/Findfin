package org.chapp.findfin.core.design.ui.foundation

import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test

class ScreenStateViewTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testEmpty() {
        val state = mutableStateOf(ScreenState.Empty)

        composeTestRule.setContent {
            AppContent {
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
        val state = mutableStateOf(ScreenState.Loading)

        composeTestRule.setContent {
            AppContent {
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
        val state = mutableStateOf(ScreenState.Error<String, String>(error = "test-data"))

        composeTestRule.setContent {
            AppContent {
                ScreenStateView(
                    state = state,
                    error = { data ->
                        Text(text = "data: $data")
                    },
                    success = {},
                )
            }
        }

        composeTestRule
            .onNodeWithText("data: test-data")
            .assertIsDisplayed()
    }

    @Test
    fun testSuccess() {
        val state = mutableStateOf(ScreenState.Success<String, Nothing>("test-success"))

        composeTestRule.setContent {
            AppContent {
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
