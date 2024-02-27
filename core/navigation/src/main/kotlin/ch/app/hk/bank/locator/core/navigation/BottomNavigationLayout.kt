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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun <T : BottomNavigationTab> BottomNavigationLayout(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    bottomTabItems: List<T>,
    content: @Composable (T) -> Unit,
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
                    navController.navigate(tab.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                },
            )
        }
    }
}

@Composable
private fun RowScope.BottomNavigationItemComponent(
    tab: BottomNavigationTab,
    isSelected: (BottomNavigationTab) -> Boolean,
    onTabClick: (BottomNavigationTab) -> Unit,
) {
    NavigationBarItem(
        modifier = Modifier.testTag(tab.route),
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

@Composable
private fun <T : BottomNavigationTab> BottomNavigationContent(
    navController: NavHostController,
    paddingValues: PaddingValues,
    bottomTabItems: List<T>,
    content: @Composable (T) -> Unit,
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

interface BottomNavigationTab {
    val route: String

    @get:DrawableRes
    val iconDrawableResource: Int

    @get:StringRes
    val textStringResource: Int
}
