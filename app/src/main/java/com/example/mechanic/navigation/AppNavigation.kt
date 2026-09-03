package com.example.mechanic.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mechanic.screens.components.ErrorView
import com.example.mechanic.screens.components.LoadingView
import com.example.mechanic.screens.details.MechanicDetailsScreen
import com.example.mechanic.screens.details.MechanicDetailsViewModel
import com.example.mechanic.screens.details.MechanicDetailsViewModelFactory
import com.example.mechanic.screens.home.HomeScreen

object Routes {
    const val HOME = "home"
    const val DETAILS = "details"
    const val REQUEST_SERVICE = "request_service"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.HOME) {
        composable(route = Routes.HOME) {
            HomeScreen(
                onMechanicClick = { mechanicId ->
                    navController.navigate("${Routes.DETAILS}/$mechanicId")
                }
            )
        }

        composable(
            route = "${Routes.DETAILS}/{mechanicId}",
            arguments = listOf(
                navArgument("mechanicId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->

            val mechanicId = backStackEntry.arguments?.getString("mechanicId") ?: return@composable
            val viewModel: MechanicDetailsViewModel = viewModel(factory = MechanicDetailsViewModelFactory(mechanicId))
            val uiState by viewModel.uiState.collectAsState()

            when {
                uiState.isLoading -> {
                    LoadingView()
                }
                uiState.errorMessage != null -> {
                    ErrorView(
                        message = uiState.errorMessage!!,
                        onRetry = {
                            // Retry can be added later.
                        }
                    )
                }
                uiState.mechanic != null -> {
                    MechanicDetailsScreen(
                        mechanic = uiState.mechanic!!,
                        onBackClick = {
                            navController.popBackStack()
                        },
                        onRequestServiceClick = {
                            navController.navigate("${Routes.REQUEST_SERVICE}/${mechanicId}")
                        }
                    )
                }
            }
        }
    }
}