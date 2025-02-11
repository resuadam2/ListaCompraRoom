package com.example.listacomprapersistente.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    navigateToProductDetails: (String) -> Unit,
    navigateToProductUpdate: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ListViewModel = viewModel(factory = AppViewModelProvider.Factory)
    ) {
    val state by viewModel.listUiState.collectAsState()

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text("Lista de la compra") },
                colors = TopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    scrolledContainerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        floatingActionButton = {
            // FloatingActionButton para añadir un producto
            FloatingActionButton(
                onClick = navigateToAddProduct,
                containerColor = MaterialTheme.colorScheme.secondary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    tint = MaterialTheme.colorScheme.onSecondary,
                    contentDescription = "Añadir producto"
                )
            }
        },
        bottomBar =  {
            BottomAppBar (
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
            ){
                TotalRow(
                    totalQuantity = state.totalQuantity.toString(),
                    totalPrice = state.totalPrice.toString()
                )
            }
        }
    ) {
        Column (
            modifier = modifier.padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (state.isLoading) {
                CircularProgressIndicator()
            } else {
                // Necesitamos definir un coroutineScope para poder lanzar coroutines en Composables
                val coroutineScope = rememberCoroutineScope()
                state.products.forEach { product ->
                    ProductItem(
                        product = product,
                        onView = {
                            navigateToProductDetails(
                                product.name
                            )
                        },
                        onEdit = {
                            navigateToProductUpdate(
                                product.name
                            )
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
}

@Composable
fun TotalRow(totalQuantity: Any, totalPrice: Any) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
    ){
        Text("Total", fontSize = 20.sp)
        Text("Cantidad: $totalQuantity", fontSize = 20.sp)
        Text("Precio: $totalPrice", fontSize = 20.sp)
    }
}

@Composable
fun ProductItem(product: Product, onView: () -> Unit, onEdit: () -> Unit, onDelete: () -> Unit) {
    // Implementación de la vista de un producto
    Card (
        modifier = Modifier.background(Color.Transparent).padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Row (
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = product.name,
                modifier = Modifier.padding(16.dp),
                fontSize = 20.sp)
            Spacer(modifier = Modifier.weight(1f))
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

@Preview(showBackground = true)
@Composable
fun ProductItemPreview() {
    ProductItem(
        product = Product("Producto 1", 1, 1.0),
        onView = {},
        onEdit = {},
        onDelete = {}
    )
}

@Preview(showBackground = true)
@Composable
fun ListScreenPreview() {
    ListScreen(
        navigateToAddProduct = {},
        navigateToProductDetails = {},
        navigateToProductUpdate = {},
        modifier = Modifier
    )
}