package ch.app.hk.bank.locator.core.navigation

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
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

/**
 * Composable function that sets up a bottom navigation layout with a scaffold.
 *
 * @param modifier The modifier to be applied to the layout.
 * @param navController The navigation controller for managing navigation.
 * @param bottomTabItems The list of bottom navigation tabs.
 * @param content The content to be displayed for each tab.
 */
@Composable
fun BottomNavigationLayout(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    bottomTabItems: List<BottomNavigationTab>,
    content: @Composable (BottomNavigationTab) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                bottomTabItems = bottomTabItems,
            )
        },
    ) { innerPadding ->
        BottomNavigationContent(
            navController = navController,
            paddingValues = innerPadding,
            bottomTabItems = bottomTabItems,
            content = content,
        )
    }
}

/**
 * Composable function that sets up the bottom navigation bar.
 *
 * @param navController The navigation controller for managing navigation.
 * @param bottomTabItems The list of bottom navigation tabs.
 */
@Composable
private fun BottomNavigationBar(
    navController: NavHostController,
    bottomTabItems: List<BottomNavigationTab>,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    NavigationBar {
        bottomTabItems.forEach { tab ->
            BottomNavigationItemComponent(
                tab = tab,
                isSelected = {
                    navBackStackEntry?.destination?.hierarchy?.any { it.route == tab.route } == true
                },
                onTabClick = {
                    navController.routeToBottomNavigationTab(tab)
                },
            )
        }
    }
}

/**
 * Extension function for navigating to a bottom navigation tab.
 *
 * @param tab The bottom navigation tab to navigate to.
 */
fun NavHostController.routeToBottomNavigationTab(tab: BottomNavigationTab) {
    navigate(tab.route) {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(graph.findStartDestination().id) { saveState = true }
        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
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
 * @param content The content to be displayed for each tab.
 */
@Composable
private fun BottomNavigationContent(
    navController: NavHostController,
    paddingValues: PaddingValues,
    bottomTabItems: List<BottomNavigationTab>,
    content: @Composable (BottomNavigationTab) -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = bottomTabItems.first().route,
        modifier = Modifier.padding(paddingValues),
    ) {
        bottomTabItems.forEach { tab ->
            composable(route = tab.route) {
                content(tab)
            }
        }
    }
}

/**
 * Data class representing a bottom navigation tab.
 *
 * @property route The route associated with the tab.
 * @property iconDrawableResource The drawable resource ID for the tab's icon.
 * @property textStringResource The string resource ID for the tab's text.
 */
data class BottomNavigationTab(
    val route: String,
    @get:DrawableRes
    val iconDrawableResource: Int,
    @get:StringRes
    val textStringResource: Int,
)
