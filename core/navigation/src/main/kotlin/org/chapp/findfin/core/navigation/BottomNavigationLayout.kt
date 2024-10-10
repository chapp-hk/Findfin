package org.chapp.findfin.core.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

/**
 * Composable function that sets up a bottom navigation layout with a scaffold.
 *
 * @param modifier The modifier to be applied to the layout.
 * @param navController The navigation controller for managing navigation.
 * @param bottomTabItems The list of bottom navigation tabs.
 * @param onTabClick A lambda to handle tab click events.
 * @param navGraphBuilder The builder for the navigation graph.
 */
@Composable
fun BottomNavigationLayout(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    bottomTabItems: List<BottomNavigationTab>,
    onTabClick: (BottomNavigationTab) -> Unit,
    navGraphBuilder: NavGraphBuilder.() -> Unit,
) {
    Scaffold(
        modifier = modifier,
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                bottomTabItems = bottomTabItems,
                onTabClick = onTabClick,
            )
        },
    ) { innerPadding ->
        BottomNavigationContent(
            navController = navController,
            paddingValues = innerPadding,
            bottomTabItems = bottomTabItems,
            navGraphBuilder = navGraphBuilder,
        )
    }
}

/**
 * Composable function that sets up the bottom navigation bar.
 *
 * @param navController The navigation controller for managing navigation.
 * @param bottomTabItems The list of bottom navigation tabs.
 * @param onTabClick A lambda to handle tab click events.
 */
@Composable
private fun BottomNavigationBar(
    navController: NavHostController,
    bottomTabItems: List<BottomNavigationTab>,
    onTabClick: (BottomNavigationTab) -> Unit,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    NavigationBar {
        bottomTabItems.forEach { tab ->
            BottomNavigationItemComponent(
                tab = tab,
                isSelected = {
                    navBackStackEntry
                        ?.destination
                        ?.route
                        ?.startsWith(it::class.qualifiedName.orEmpty()) ?: false
                },
                onTabClick = onTabClick,
            )
        }
    }
}

/**
 * Composable function that sets up a bottom navigation item component.
 *
 * @param tab The bottom navigation tab.
 * @param isSelected A lambda to determine if the tab is selected.
 * @param onTabClick A lambda to handle tab click events.
 */
@Composable
private fun RowScope.BottomNavigationItemComponent(
    tab: BottomNavigationTab,
    isSelected: (BottomNavigationTab) -> Boolean,
    onTabClick: (BottomNavigationTab) -> Unit,
) {
    NavigationBarItem(
        modifier = Modifier.semantics { contentDescription = tab.route },
        icon = {
            Icon(
                imageVector = ImageVector.vectorResource(id = tab.iconDrawableResource),
                contentDescription = tab.route,
            )
        },
        label = { Text(text = stringResource(id = tab.textStringResource)) },
        selected = isSelected(tab),
        onClick = { onTabClick(tab) },
    )
}

/**
 * Composable function that sets up the content for the bottom navigation.
 *
 * @param navController The navigation controller for managing navigation.
 * @param paddingValues The padding values to be applied to the content.
 * @param bottomTabItems The list of bottom navigation tabs.
 * @param navGraphBuilder The builder for the navigation graph.
 */
@Composable
private fun BottomNavigationContent(
    navController: NavHostController,
    paddingValues: PaddingValues,
    bottomTabItems: List<BottomNavigationTab>,
    navGraphBuilder: NavGraphBuilder.() -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = bottomTabItems.first(),
        modifier = Modifier.padding(paddingValues),
        builder = navGraphBuilder,
    )
}

/**
 * Interface representing a bottom navigation tab.
 *
 * @property route The route associated with the tab.
 * @property iconDrawableResource The drawable resource ID for the tab's icon.
 * @property textStringResource The string resource ID for the tab's text.
 */
interface BottomNavigationTab {
    val route: String

    @get:DrawableRes
    val iconDrawableResource: Int

    @get:StringRes
    val textStringResource: Int
}
