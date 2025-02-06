package com.example.listacomprapersistente.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.listacomprapersistente.ui.navigation.NavigationDestination

/**
 * Vamos a reutilizar la pantalla de añadir producto para la pantalla de editar producto.
 * Para ello, vamos a añadir un argumento a la ruta de la pantalla de añadir producto.
 * De esta forma, cuando naveguemos a la pantalla de añadir producto, podremos pasarle el nombre
 * del producto que queremos editar.
 * También vamos a utilizar esta pantalla para ver en detalle un producto.
 * Para ello, vamos a añadir un argumento a la ruta de la pantalla de añadir producto.
 * De esta forma, cuando naveguemos a la pantalla de añadir producto, podremos pasarle el nombre
 * del producto que queremos ver en detalle.
 */

object ProductAddDestination : NavigationDestination {
    override val route = "productAdd"
}

object ProductDetailsDestination : NavigationDestination {
    override val route = "productDetails"
    const val productNameArg = "productName"
    val routeWithArgs = "$route/{$productNameArg}"
}

object ProductUpdateDestination : NavigationDestination {
    override val route = "productUpdate"
    const val productNameArg = "productName"
    val routeWithArgs = "$route/{$productNameArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductAddScreen(
    action: String,
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit = {},
    modifier: Modifier = Modifier,
    ) {
    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    when (action) {
                        "Add" -> { Text("Añadir producto") }
                        "Details" -> { Text("Detalles del producto") }
                        "Update" -> { Text("Editar producto") }
                        else -> { Text("Añadir producto") }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) {
        Column (
            modifier = modifier.padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Aquí iría el formulario para añadir un producto
        }
    }

}

