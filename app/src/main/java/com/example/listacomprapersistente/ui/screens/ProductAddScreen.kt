package com.example.listacomprapersistente.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
            modifier = modifier.padding(it).fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            when (action) {
                "Add" -> { AddProductForm() }
                "Details" -> { DetailsProductForm() }
                "Update" -> { EditProductForm() }
                else -> { AddProductForm() }
            }
        }
    }

}

@Composable
fun EditProductForm() {
    TODO("Not yet implemented")
}

@Composable
fun DetailsProductForm() {
    TODO("Not yet implemented")
}

@Composable
fun AddProductForm(
    name: String = "",
    quantity: Int = 0,
    price: Double = 0.0,
    changeName: (String) -> Unit = {},
    changeQuantity: (Int) -> Unit = {},
    changePrice: (Double) -> Unit = {},
    onAdd: () -> Unit = {},
    onCancel: () -> Unit = {}
) {
    OutlinedTextField(
        value = name,
        modifier = Modifier.fillMaxWidth(),
        onValueChange = { changeName(it) },
        maxLines = 1,
        label = { Text("Producto") }
    )
    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Cantidad")
        Spacer(modifier = Modifier.padding(64.dp, 0.dp))
        OutlinedTextField(
            value = quantity.toString(),
            onValueChange = {
                changeQuantity(it.toInt())
            },
            modifier = Modifier.width(86.dp),
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )
    }

    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Precio")
        Spacer(modifier = Modifier.padding(64.dp, 0.dp))
        OutlinedTextField(
            value = price.toString(),
            onValueChange = {
                changePrice(it.toDouble())
            },
            modifier = Modifier.width(86.dp),
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        )
    }
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
            onClick = onAdd,
        ) {
            Text("Añadir")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductAddScreenPreview() {
    ProductAddScreen(
        action = "Add",
        navigateBack = {},
        onNavigateUp = {}
    )
}
