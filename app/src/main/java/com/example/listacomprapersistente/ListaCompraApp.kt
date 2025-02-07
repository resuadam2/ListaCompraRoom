package com.example.listacomprapersistente

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.listacomprapersistente.ui.navigation.AppNavHost

/**
 * Top level composable that represents screens for the application
 */

@Composable
fun ListaCompraApp(navController: NavHostController = rememberNavController()) {
    AppNavHost(navController)
}