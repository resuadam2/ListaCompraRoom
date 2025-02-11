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
import com.example.listacomprapersistente.ui.screens.ProductDetailScreen
import com.example.listacomprapersistente.ui.screens.ProductDetailsDestination
import com.example.listacomprapersistente.ui.screens.ProductUpdateDestination
import com.example.listacomprapersistente.ui.screens.ProductUpdateScreen

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
                    navController.navigate("${ProductDetailsDestination.route}/$it")
                },
                navigateToProductUpdate = {
                    navController.navigate("${ProductUpdateDestination.route}/$it")
                }
            )
        }
        composable(
            route = ProductAddDestination.route
        ) {
            ProductAddScreen(
                navigateBack = { navController.popBackStack() },
            )
        }
        composable(
            route = ProductDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(ProductDetailsDestination.productNameArg) {
                type = NavType.StringType
            })
            ) {
            ProductDetailScreen(
                navigateBack = { navController.popBackStack() },
                navigateToEditProduct = {
                    navController.navigate("${ProductUpdateDestination.route}/$it")
                }
            )
        }
        composable(
            route = ProductUpdateDestination.routeWithArgs,
            arguments = listOf(navArgument(ProductUpdateDestination.productNameArg) {
                type = NavType.StringType
            })
        ) {
            ProductUpdateScreen(
                navigateBack = { navController.popBackStack() },
            )
        }
    }
}