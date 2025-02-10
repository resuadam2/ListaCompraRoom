package com.example.listacomprapersistente.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.listacomprapersistente.ui.screens.ListDestination
import com.example.listacomprapersistente.ui.screens.ListScreen
import com.example.listacomprapersistente.ui.screens.ProductAddDestination
import com.example.listacomprapersistente.ui.screens.ProductAddScreen
import com.example.listacomprapersistente.ui.screens.ProductDetailsDestination
import com.example.listacomprapersistente.ui.screens.ProductUpdateDestination

@Composable
fun AppNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = ListDestination.route,
    ) {
        composable(route = ListDestination.route) {
            ListScreen(
                navigateToAddProduct = {
                    navController.navigate(ProductAddDestination.route)
                },
                navigateToProductDetails = {
                    navController.navigate(ProductDetailsDestination.routeWithArgs)
                },
                navigateToProductUpdate = {
                    navController.navigate(ProductUpdateDestination.routeWithArgs)
                }
            )
        }
        composable(
            route = ProductAddDestination.route
        ) {
            ProductAddScreen(
                action = "Add",
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
        composable(
            route = ProductDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(ProductDetailsDestination.productNameArg) {
                type = NavType.StringType
            })
            ) {
            ProductAddScreen(
                action = "Details",
                product = it.arguments?.getString(ProductDetailsDestination.productNameArg),
                navigateBack = { navController.popBackStack() },
            )
        }
        composable(
            route = ProductUpdateDestination.routeWithArgs,
            arguments = listOf(navArgument(ProductUpdateDestination.productNameArg) {
                type = NavType.StringType
            })
        ) {
            ProductAddScreen(
                action = "Update",
                product = it.arguments?.getString(ProductUpdateDestination.productNameArg),
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp()}
            )
        }
    }
}