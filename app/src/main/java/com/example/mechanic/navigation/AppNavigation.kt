package com.example.mechanic.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mechanic.screens.components.ErrorView
import com.example.mechanic.screens.components.LoadingView
import com.example.mechanic.screens.details.MechanicDetailsScreen
import com.example.mechanic.screens.details.MechanicDetailsViewModel
import com.example.mechanic.screens.home.HomeScreen
import com.example.mechanic.screens.request.RequestServiceScreen

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
            val viewModel: MechanicDetailsViewModel = hiltViewModel()
            
            remember(mechanicId) {
                viewModel.setMechanicId(mechanicId)
            }
            
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

        composable(
            route = "${Routes.REQUEST_SERVICE}/{mechanicId}",
            arguments = listOf(navArgument("mechanicId") { type = NavType.StringType })
        ) { backStackEntry ->

            val mechanicId = backStackEntry.arguments?.getString("mechanicId") ?: return@composable
            val detailsViewModel: MechanicDetailsViewModel = hiltViewModel()
            
            remember(mechanicId) {
                detailsViewModel.setMechanicId(mechanicId)
            }
            
            val uiState by detailsViewModel.uiState.collectAsState()

            when {
                uiState.isLoading -> {
                    LoadingView()
                }
                uiState.errorMessage != null -> {
                    ErrorView(
                        message = uiState.errorMessage!!,
                        onRetry = { navController.popBackStack() }
                    )
                }
                uiState.mechanic != null -> {
                    val mechanic = uiState.mechanic!!
                    RequestServiceScreen(
                        services =
                            mechanic.services
                                .split(", ")
                                .filter { it.isNotBlank() },
                        onBackClick = { navController.popBackStack() },
                        onSubmitted = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}