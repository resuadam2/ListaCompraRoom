package com.example.listacomprapersistente.ui.screens

import android.database.sqlite.SQLiteConstraintException
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.listacomprapersistente.data.Product
import com.example.listacomprapersistente.data.PruductRepository
import java.text.NumberFormat

/**
 * UI state for the ProductAddScreen
 */
data class ProductAddUiState(
    val productDetails: ProductDetails = ProductDetails(),
    val isSaveButtonEnabled: Boolean = false,
)

/**
 * UI representation of the product details
 */
data class ProductDetails(
    val productName: String = "",
    val productPrice: String = "",
    val productQuantity: String = "",
)

/**
 * Extension function to convert a [ProductDetails] to a [Product]
 * @return a [Product] with the same values as the [ProductDetails]
 * If the conversion is not possible, the default values are used
 */
fun ProductDetails.toProduct(): Product {
    return Product(
        name = productName,
        price = productPrice.toDoubleOrNull() ?: 1.0,
        quantity = productQuantity.toIntOrNull() ?: 1
    )
}

/**
 * Extension function to format the price of a [Product]
 */
fun Product.formatedPrice(): String {
    return NumberFormat.getCurrencyInstance().format(price)
}

/**
 * Extension function to convert a [Product] to a [ProductDetails]
 * @return a [ProductDetails] with the same values as the [Product]
 */

fun Product.toProductDetails(): ProductDetails {
    return ProductDetails(
        productName = name,
        productPrice = price.toString(),
        productQuantity = quantity.toString()
    )
}

/**
 * Extension function to convert a [Product] to a [ProductAddUiState]
 */
fun Product.toProductAddUiState(): ProductAddUiState {
    return ProductAddUiState(
        productDetails = toProductDetails(),
        isSaveButtonEnabled = true
    )
}

class ProductAddViewModel (
    private val productRepository: PruductRepository,
) : ViewModel() {

    var productAddUiState by mutableStateOf(ProductAddUiState())
        private set

    /**
     * Actualiza el estado de la UI con los detalles del producto
     */
    fun updateUiState(productDetails: ProductDetails) {
        productAddUiState = productAddUiState.copy(
            productDetails = productDetails,
            isSaveButtonEnabled = validateInput(productDetails)
        )
    }

    /**
     * Guarda un producto en la base de datos
     * Captura la SQLException que se produce si el producto ya existe y devuelve false en ese caso
     */
    suspend fun saveProduct(): Boolean {
        if (validateInput(productAddUiState.productDetails)) {
            try {
                productRepository.insertProduct(productAddUiState.productDetails.toProduct())
                return true
            } catch (e: SQLiteConstraintException) {
                updateUiState(productAddUiState.productDetails.copy(productName = ""))
                return false
            }
        } else return false
    }

    /**
     * Valida que el formulario no tenga campos vacíos
     */
    private fun validateInput(productDetails: ProductDetails = productAddUiState.productDetails): Boolean {
        return with(productDetails) {
            productName.isNotBlank() && productPrice.isNotBlank() && productQuantity.isNotBlank()
        }
    }
}


