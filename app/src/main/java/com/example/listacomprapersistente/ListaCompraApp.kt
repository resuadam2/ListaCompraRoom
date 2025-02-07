package com.example.listacomprapersistente

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.listacomprapersistente.data.AppContainer
import com.example.listacomprapersistente.ui.navigation.AppNavHost

/**
 * Top level composable that represents screens for the application
 */

@Composable
fun ListaCompraScreenContainer(navController: NavHostController = rememberNavController()) {
    AppNavHost(navController)
}

class ListaCompraApp : Application() {
    lateinit var appContainer : AppContainer

    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer(this)
    }
}