package com.example.listacomprapersistente.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.listacomprapersistente.data.Product
import com.example.listacomprapersistente.ui.AppViewModelProvider
import com.example.listacomprapersistente.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

object ListDestination : NavigationDestination {
    override val route = "list"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    navigateToAddProduct: () -> Unit,
    navigateToProductDetails: () -> Unit,
    navigateToProductUpdate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ListViewModel = viewModel(factory = AppViewModelProvider.Factory)
    ) {
    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text("Lista de la compra") }
            )
        },
        floatingActionButton = {
            // FloatingActionButton para añadir un producto
            FloatingActionButton(
                onClick = navigateToAddProduct
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Añadir producto"
                )
            }
        }
    ) {
        Column (
            modifier = modifier.padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val state = viewModel.listUiState.collectAsState()
            // Necesitamos definir un coroutineScope para poder lanzar coroutines en Composables
            val coroutineScope = rememberCoroutineScope()
            state.value.products.forEach { product ->
                ProductItem(
                    product = product,
                    onView = {
                        navigateToProductDetails()
                    },
                    onEdit = {
                        navigateToProductUpdate()
                    },
                    onDelete = {
                        coroutineScope.launch {
                            viewModel.deleteProduct(product)
                        }
                    }
                )

            }
        }
    }
}

@Composable
fun ProductItem(product: Product, onView: () -> Unit, onEdit: () -> Unit, onDelete: () -> Unit) {
    // Implementación de la vista de un producto
    Card (
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Row (
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = product.name)
            IconButton(
                onClick = onView
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Ver producto"
                )
            }
            IconButton(
                onClick = onEdit
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Editar producto"
                )
            }
            IconButton(
                onClick = onDelete
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar producto"
                )
            }
        }
    }

}
