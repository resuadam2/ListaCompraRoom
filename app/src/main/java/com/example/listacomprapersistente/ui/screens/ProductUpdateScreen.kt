package com.example.listacomprapersistente.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.listacomprapersistente.ui.AppViewModelProvider
import com.example.listacomprapersistente.ui.navigation.NavigationDestination
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import kotlinx.coroutines.launch


object ProductUpdateDestination : NavigationDestination {
    override val route = "productUpdate"
    const val productNameArg = "productName"
    val routeWithArgs = "$route/{$productNameArg}"
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductUpdateScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProductUpdateViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
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
            modifier = modifier
                .padding(it)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            UpdateProductForm(
                productDetailUiState = viewModel.productUpdateUiState,
                onProductValueChanged = viewModel::updateUiState,
                onEdit = {
                    coroutineScope.launch {
                        viewModel.updateProduct()
                        navigateBack()
                    }
                },
                onCancel = navigateBack
            )
        }
    }
}


@Composable
fun UpdateProductForm(
    productDetailUiState: ProductUpdateUiState,
    onProductValueChanged: (ProductDetails) -> Unit,
    onEdit: () -> Unit = {},
    onCancel: () -> Unit = {}
) {
    val focusManager = LocalFocusManager.current
    Text("Producto: ${productDetailUiState.productDetails.productName}")
    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.padding(32.dp, 0.dp))
        Text("Cantidad")
        Spacer(modifier = Modifier.padding(32.dp, 0.dp))
        OutlinedTextField(
            value = productDetailUiState.productDetails.productQuantity,
            onValueChange = {
                onProductValueChanged(productDetailUiState.productDetails.copy(productQuantity = it))
            },
            modifier = Modifier
                .width(86.dp)
                .onFocusChanged {
                    if (it.hasFocus) {
                        onProductValueChanged(
                            productDetailUiState.productDetails.copy(
                                productQuantity = ""
                            )
                        )
                    } else if (productDetailUiState.productDetails.productQuantity.isBlank()) {
                        onProductValueChanged(
                            productDetailUiState.productDetails.copy(
                                productQuantity = "1"
                            )
                        )
                    }
                },
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )
    }

    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.padding(32.dp, 0.dp))
        Text("Precio")
        Spacer(modifier = Modifier.padding(32.dp, 0.dp))
        OutlinedTextField(
            value = productDetailUiState.productDetails.productPrice,
            onValueChange = {
                onProductValueChanged(productDetailUiState.productDetails.copy(productPrice = it))
            },
            modifier = Modifier
                .width(86.dp)
                .onFocusChanged {
                    if (it.hasFocus) {
                        onProductValueChanged(productDetailUiState.productDetails.copy(productPrice = ""))
                    } else if (productDetailUiState.productDetails.productPrice.isBlank()) {
                        onProductValueChanged(productDetailUiState.productDetails.copy(productPrice = "1.0"))
                    }
                },
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        )
    }
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
            onClick = onEdit,
            enabled = productDetailUiState.isSaveButtonEnabled
        ) {
            Text("Editar")
        }
    }
}