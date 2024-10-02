package ch.app.hk.bank.locator.feature.home.ui.container.view

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import ch.app.hk.bank.locator.feature.home.ui.R
import ch.app.hk.bank.locator.feature.home.ui.nearby.viewmodel.NearByViewModel
import ch.app.hk.bank.locator.testing.instrument.HiltComponentActivity
import ch.app.hk.bank.locator.testing.instrument.getResourceString
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.mockk
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

//    @Test
//    fun homeContainerContent_displaysLoadingIndicator() {
//        // Set up the mock function
//        every { nearByViewModel.uiState } returns
//            MutableStateFlow(NearByUiState.NearByLoading)
//
//        // Start the HomeContainerContent composable
//        composeTestRule.setContent {
//            HomeContainerContent(
//                nearByViewModel = nearByViewModel,
//                onSearch = {},
//            )
//        }
//
//        // Check if AppSearchBar and Finding are displayed
//        composeTestRule
//            .onNodeWithContentDescription(getResourceString(R.string.home_content_description_loading))
//            .assertIsDisplayed()
//    }

    @Test
    fun homeContainerContent_displaysNearByLocationDisabledResult() {
//        // Set up the mock function
//        every { nearByViewModel.uiState } returns
//            MutableStateFlow(ScreenState.Error(NearByUiState.Error(NearByError.GPS_IS_OFF)))
//
//        // Start the HomeContainerContent composable
//        composeTestRule.setContent {
//            HomeContainerContent(
//                onSearch = {},
//            )
//        }
//
//        // Check if NearByLocationDisabledResult is displayed
//        composeTestRule
//            .onNodeWithContentDescription(getResourceString(R.string.home_content_description_location_disabled))
//            .assertIsDisplayed()
    }
//
//    @Test
//    fun homeContainerContent_displaysLocationPermissionDeniedResult() {
//        // Set up the mock function
//        every { nearByViewModel.uiState } returns
//            MutableStateFlow(ScreenState.Error(NearByUiState.Error(NearByError.PERMISSION_NOT_GRANTED)))
//
//        // Start the HomeContainerContent composable
//        composeTestRule.setContent {
//            HomeContainerContent(
//                nearByViewModel = nearByViewModel,
//                onSearch = {},
//            )
//        }
//
//        // Check if LocationPermissionDeniedResult is displayed
//        composeTestRule
//            .onNodeWithContentDescription(getResourceString(R.string.home_content_description_location_permission_denied))
//            .assertIsDisplayed()
//    }
//
//    @Test
//    fun homeContainerContent_displaysDeviceNoGpsResult() {
//        // Set up the mock function
//        every { nearByViewModel.uiState } returns
//            MutableStateFlow(ScreenState.Error(NearByUiState.Error(NearByError.GPS_NOT_SUPPORTED)))
//
//        // Start the HomeContainerContent composable
//        composeTestRule.setContent {
//            HomeContainerContent(
//                nearByViewModel = nearByViewModel,
//                onSearch = {},
//            )
//        }
//
//        // Check if NearByNoGpsResult is displayed
//        composeTestRule
//            .onNodeWithText(getResourceString(R.string.home_label_nearby_device_no_gps))
//            .assertIsDisplayed()
//    }
//
//    @Test
//    fun homeContainerContent_displaysEmptyResult() {
//        // Set up the mock function
//        every { nearByViewModel.uiState } returns
//            MutableStateFlow(ScreenState.Empty)
//
//        // Start the HomeContainerContent composable
//        composeTestRule.setContent {
//            HomeContainerContent(
//                nearByViewModel = nearByViewModel,
//                onSearch = {},
//            )
//        }
//
//        // Check if EmptyResult is displayed
//        composeTestRule
//            .onNodeWithText(getResourceString(R.string.home_label_nearby_no_services))
//            .assertIsDisplayed()
//    }
//
//    @Test
//    fun homeContainerContent_displaysServiceItems() {
//        // Set up the mock function
//        val mockList = (1..10).map { mockk<NearByItemUiModel>(relaxed = true) }
//        every { nearByViewModel.uiState } returns
//            MutableStateFlow(ScreenState.Success(NearByUiState.Service(mockList)))
//
//        // Start the HomeContainerContent composable
//        composeTestRule.setContent {
//            HomeContainerContent(
//                nearByViewModel = nearByViewModel,
//                onSearch = {},
//            )
//        }
//
//        // Check if service list is displayed
//        composeTestRule
//            .onAllNodesWithContentDescription(getResourceString(R.string.home_content_description_nearby_service))
//            .assertCountEquals(10)
//    }
}
