package com.example.listacomprapersistente.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.listacomprapersistente.ui.navigation.NavigationDestination

object ProductUpdateDestination : NavigationDestination {
    override val route = "productUpdate"
    const val productNameArg = "productName"
    val routeWithArgs = "$route/{$productNameArg}"
}

@Composable
fun EditProductForm(
    name: String = "",
    quantity: Int = 0,
    price: Double = 0.0,
    changeQuantity: (Int) -> Unit = {},
    changePrice: (Double) -> Unit = {},
    onEdit: () -> Unit = {},
    onCancel: () -> Unit = {}
) {
    Text(name)
    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.padding(32.dp, 0.dp))
        Text("Cantidad")
        Spacer(modifier = Modifier.padding(32.dp, 0.dp))
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
        Spacer(modifier = Modifier.padding(32.dp, 0.dp))
        Text("Precio")
        Spacer(modifier = Modifier.padding(32.dp, 0.dp))
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
        ) {
            Text("Editar")
        }
    }
}