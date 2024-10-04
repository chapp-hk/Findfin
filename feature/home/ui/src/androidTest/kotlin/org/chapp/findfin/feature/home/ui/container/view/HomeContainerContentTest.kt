package org.chapp.findfin.feature.home.ui.container.view

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.chapp.findfin.core.location.setting.helper.LocalSettingHelper
import org.chapp.findfin.core.location.setting.helper.SettingHelper
import org.chapp.findfin.core.location.setting.state.LocationSettingStatus
import org.chapp.findfin.feature.home.ui.R
import org.chapp.findfin.feature.home.ui.nearby.model.NearByItemUiModel
import org.chapp.findfin.feature.home.ui.nearby.model.NearByUiState
import org.chapp.findfin.feature.home.ui.nearby.viewmodel.NearByViewModel
import org.chapp.findfin.testing.instrument.HiltComponentActivity
import org.chapp.findfin.testing.instrument.getResourceString
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class HomeContainerContentTest {
    @get:Rule(order = 0)
    val hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltComponentActivity>()

    @BindValue
    val nearByViewModel = mockk<NearByViewModel>()
    private val settingHelper = mockk<SettingHelper>(relaxed = true)

    @Test
    fun homeContainerContent_displaysAppSearchBarAndFinding() {
        // Set up the mock function
        val onSearch: (String) -> Unit = {}

        // Start the HomeContainerContent composable
        composeTestRule.setContent {
            HomeContainerContent(
                onSearch = onSearch,
            )
        }

        // Check if AppSearchBar and Finding are displayed
        composeTestRule
            .onNodeWithContentDescription(getResourceString(R.string.home_content_description_search))
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription(getResourceString(R.string.home_content_description_finding))
            .assertIsDisplayed()
    }

    @Test
    fun homeContainerContent_displaysNearByLocationDisabledResult() {
        // mocks
        every { nearByViewModel.uiState } returns MutableStateFlow(NearByUiState.Error)
        every { settingHelper.getSettings() } returns LocationSettingStatus.Disabled

        // Start the HomeContainerContent composable
        composeTestRule.setContent {
            CompositionLocalProvider(LocalSettingHelper provides settingHelper) {
                HomeContainerContent(
                    onSearch = {},
                )
            }
        }

        // Check if NearByLocationDisabledResult is displayed
        composeTestRule
            .onNodeWithContentDescription(getResourceString(R.string.home_content_description_location_disabled))
            .assertIsDisplayed()
    }

    @Test
    fun homeContainerContent_displaysLocationPermissionDeniedResult() {
        // Set up the mock function
        every { nearByViewModel.uiState } returns MutableStateFlow(NearByUiState.Error)
        every { settingHelper.getSettings() } returns LocationSettingStatus.Enabled

        // Start the HomeContainerContent composable
        composeTestRule.setContent {
            CompositionLocalProvider(LocalSettingHelper provides settingHelper) {
                HomeContainerContent(
                    nearByViewModel = nearByViewModel,
                    onSearch = {},
                )
            }
        }

        // Check if LocationPermissionDeniedResult is displayed
        composeTestRule
            .onNodeWithContentDescription(getResourceString(R.string.home_content_description_location_permission_denied))
            .assertIsDisplayed()
    }

    @Test
    fun homeContainerContent_displaysDeviceNoGpsResult() {
        // Set up the mock function
        every { nearByViewModel.uiState } returns MutableStateFlow(NearByUiState.Error)
        every { settingHelper.getSettings() } returns LocationSettingStatus.NoSensor

        // Start the HomeContainerContent composable
        composeTestRule.setContent {
            CompositionLocalProvider(LocalSettingHelper provides settingHelper) {
                HomeContainerContent(
                    nearByViewModel = nearByViewModel,
                    onSearch = {},
                )
            }
        }

        // Check if NearByNoGpsResult is displayed
        composeTestRule
            .onNodeWithText(getResourceString(R.string.home_label_nearby_device_no_gps))
            .assertIsDisplayed()
    }

    @Test
    fun homeContainerContent_displaysEmptyResult() {
        // Set up the mock function
        every { nearByViewModel.uiState } returns MutableStateFlow(NearByUiState.Empty)

        // Start the HomeContainerContent composable
        composeTestRule.setContent {
            HomeContainerContent(
                nearByViewModel = nearByViewModel,
                onSearch = {},
            )
        }

        // Check if EmptyResult is displayed
        composeTestRule
            .onNodeWithText(getResourceString(R.string.home_label_nearby_no_services))
            .assertIsDisplayed()
    }

    @Test
    fun homeContainerContent_displaysServiceItems() {
        // Set up the mock function
        val mockList = (1..10).map { mockk<NearByItemUiModel>(relaxed = true) }
        every { nearByViewModel.uiState } returns MutableStateFlow(NearByUiState.Service(mockList))

        // Start the HomeContainerContent composable
        composeTestRule.setContent {
            HomeContainerContent(
                nearByViewModel = nearByViewModel,
                onSearch = {},
            )
        }

        // Check if service list is displayed
        composeTestRule
            .onAllNodesWithContentDescription(getResourceString(R.string.home_content_description_nearby_service))
            .assertCountEquals(10)
    }
}
