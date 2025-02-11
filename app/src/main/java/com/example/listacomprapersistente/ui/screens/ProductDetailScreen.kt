package com.example.listacomprapersistente.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.listacomprapersistente.ui.AppViewModelProvider
import com.example.listacomprapersistente.ui.navigation.NavigationDestination

object ProductDetailsDestination : NavigationDestination {
    override val route = "productDetails"
    const val productNameArg = "productName"
    val routeWithArgs = "$route/{$productNameArg}"
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    navigateBack: () -> Unit,
    navigateToEditProduct: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProductDetailViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text("Detalles del producto")
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
            modifier = modifier.padding(it).fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            DetailsProductForm(
                productDetailUiState = viewModel.productDetailUiState,
                onGoToEdit = { navigateToEditProduct(viewModel.productDetailUiState.productDetails.productName) },
                onCancel = navigateBack
            )
        }
    }
}


@Composable
fun DetailsProductForm(
    productDetailUiState: ProductDetailUiState,
    onGoToEdit: () -> Unit = {},
    onCancel: () -> Unit = {}
) {
    Text("Producto: ${productDetailUiState.productDetails.productName}")
    Spacer(modifier = Modifier.padding(0.dp, 16.dp))
    Text("Cantidad: ${productDetailUiState.productDetails.productQuantity}")
    Spacer(modifier = Modifier.padding(0.dp, 16.dp))
    Text("Precio: ${productDetailUiState.productDetails.productPrice}")
    Spacer(modifier = Modifier.padding(0.dp, 16.dp))

    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = onCancel,
        ) {
            Text("Cancelar")
        }
        Spacer(modifier = Modifier.padding(32.dp, 0.dp))
        Button(
            onClick = onGoToEdit,
        ) {
            Text("Editar")
        }
    }
}