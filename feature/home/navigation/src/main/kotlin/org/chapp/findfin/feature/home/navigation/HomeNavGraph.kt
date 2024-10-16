package org.chapp.findfin.feature.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import org.chapp.findfin.feature.auth.presentation.navigation.graph.AuthNavGraphDestination

fun NavGraphBuilder.homeNavGraph(navController: NavController) {
    navigation<HomeNavGraphDestination>(
        startDestination = HomeDestination,
    ) {
        composable<HomeDestination> {
            HomeBottomNavigationLayout {
                navController.navigate(
                    route = AuthNavGraphDestination,
                )
            }
        }
    }
}
