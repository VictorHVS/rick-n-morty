package com.victorhvs.rnm.presentation.navigation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.victorhvs.rnm.presentation.screens.detail.DetailScreen
import com.victorhvs.rnm.presentation.screens.list.ListScreen

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.List.route
    ) {
        composable(route = Screen.List.route) {
            ListScreen(
                onCharacterClicked = { id ->
                    navController.navigate(Screen.Details.passCharId(id))
                }
            )
        }
        composable(
            route = Screen.Details.route,
            deepLinks = listOf(navDeepLink {
                uriPattern = "https://victorhvs.com/rnm/{charId}"
                action = Intent.ACTION_VIEW
            }),
            arguments = listOf(navArgument(Screen.DETAILS_ARGUMENT_KEY) {
                type = NavType.IntType
            })
        ) {
            DetailScreen(
                charId = it.arguments?.getInt(Screen.DETAILS_ARGUMENT_KEY) ?: 1,
                onBackPressed = { navController.popBackStack() }
            )
        }
    }
}
