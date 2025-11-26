package org.chapp.findfin.feature.locator.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.serialization.Serializable
import org.chapp.findfin.core.navigation.BottomNavigationTab
import org.chapp.findfin.feature.bank.data.repo.model.BankModel
import org.chapp.findfin.feature.locator.presentation.R
import org.chapp.findfin.feature.locator.presentation.ui.map.view.ListScreen

/**
 * Composable function that displays the map screen.
 */
@Composable
fun MapDestination(modifier: Modifier = Modifier) {
    // MapScreen(modifier = modifier)
    ListScreen(
        modifier = modifier,
        banks =
            listOf(
                BankModel(
                    bankName = "Bank of China",
                    typeName = "ATM",
                    address = "123 Main St",
                    district = "Shanghai",
                    serviceHours = "Mon-Fri 9am-5pm",
                    latitude = 39.9042,
                    longitude = 116.4074,
                    type = "ATM",
                ),
                BankModel(
                    bankName = "HSBC",
                    typeName = "ATM",
                    address = "1234 Main St",
                    district = "HongKong",
                    serviceHours = "Mon-Fri 9am-5pm",
                    latitude = 22.235423,
                    longitude = 116.4074,
                    type = "BRANCH",
                ),
            ),
        selectedBank = null,
        onBankSelected = {},
    )
}

/**
 * Represents the bottom navigation tab for the map screen.
 */
@Serializable
class MapBottomTabDestination(
    override val route: String = "home-bottom-map",
    override val iconDrawableResource: Int = R.drawable.locator_ic_map,
    override val textStringResource: Int = R.string.locator_tab_map,
    val searchKeyword: String? = null,
    val searchType: MapSearchType? = null,
) : BottomNavigationTab

enum class MapSearchType {
    BRANCH,
    ATM,
}
