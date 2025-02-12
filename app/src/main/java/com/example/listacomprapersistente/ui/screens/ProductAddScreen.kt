package com.example.listacomprapersistente.ui.screens

import android.widget.Toast
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.listacomprapersistente.ui.AppViewModelProvider
import com.example.listacomprapersistente.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

object ProductAddDestination : NavigationDestination {
    override val route = "productAdd"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductAddScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProductAddViewModel = viewModel(factory = AppViewModelProvider.Factory)
    ) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                      Text("Añadir producto")
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
                AddProductForm(
                    productAddUiState = viewModel.productAddUiState,
                    /**
                     * El operador '::' se utiliza para referenciar una función.
                     * En este caso, se está referenciando la función updateUiState del ViewModel.
                     * Esto se hace para que la función updateUiState se ejecute cada vez que se
                     * modifique el valor de un campo del formulario.
                     */
                    onProductValueChanged = viewModel::updateUiState,
                    onAdd = {
                        /**
                         * Hay que tener en cuenta que si el usuario rota la pantalla rápido,
                         * la operación podría cancelarse y el producto no se guardaría en bbdd.
                         * Esto ocurre cuándo un cambio de configuración (como la rotación de la pantalla)
                         * ocurre mientras una operación asíncrona está en curso.
                         * La actividad se destruye y se vuelve a crear, pero la operación asíncrona
                         * del rememberCoroutineScope se cancela, ya que forma parte de la recomposición.
                         */
                        coroutineScope.launch {
                            if (viewModel.saveProduct()) navigateBack()
                            else {
                                // Lanzamos un Toast indicando que ya existe el elemento
                                Toast.makeText(
                                    context,
                                    "El producto ya existe",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    },
                    navigateBack = navigateBack,
                )
            }
        }
    }




@Composable
fun AddProductForm(
   productAddUiState: ProductAddUiState,
    onProductValueChanged: (ProductDetails) -> Unit,
    onAdd: () -> Unit,
    navigateBack: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        value = productAddUiState.productDetails.productName,
        modifier = Modifier.fillMaxWidth(),
        onValueChange = {
            onProductValueChanged(productAddUiState.productDetails.copy(productName = it))
                        },
        singleLine = true,
        label = { Text("Producto") }
    )
    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.padding(32.dp, 0.dp))
        Text("Cantidad")
        Spacer(modifier = Modifier.padding(32.dp, 0.dp))
        OutlinedTextField(
            value = productAddUiState.productDetails.productQuantity,
            onValueChange = {
                onProductValueChanged(productAddUiState.productDetails.copy(productQuantity = it))
            },
            modifier = Modifier.width(86.dp)
                .onFocusChanged {
                    if (it.hasFocus)  {
                        onProductValueChanged(productAddUiState.productDetails.copy(productQuantity = ""))
                    } else if (productAddUiState.productDetails.productQuantity.isBlank()) {
                        onProductValueChanged(productAddUiState.productDetails.copy(productQuantity = "1"))
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
            value = productAddUiState.productDetails.productPrice,
            onValueChange = {
                onProductValueChanged(productAddUiState.productDetails.copy(productPrice = it))
            },
            modifier = Modifier.width(86.dp)
                .onFocusChanged {
                if (it.hasFocus)  {
                    onProductValueChanged(productAddUiState.productDetails.copy(productPrice = ""))
                } else if (productAddUiState.productDetails.productPrice.isBlank()) {
                    onProductValueChanged(productAddUiState.productDetails.copy(productPrice = "1,0"))
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
            onClick = navigateBack,
        ) {
            Text("Cancelar")
        }
        Spacer(modifier = Modifier.padding(32.dp, 0.dp))
        Button(
            onClick = onAdd,
            enabled = productAddUiState.isSaveButtonEnabled
        ) {
            Text("Añadir")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductAddScreenPreview() {
    ProductAddScreen(
        navigateBack = {},
    )
}
