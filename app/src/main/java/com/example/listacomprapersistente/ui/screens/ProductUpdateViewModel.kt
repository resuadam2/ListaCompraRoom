package com.example.listacomprapersistente.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listacomprapersistente.data.PruductRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

data class ProductUpdateUiState(
    val productDetails: ProductDetails = ProductDetails(),
    val isSaveButtonEnabled: Boolean = false,
)

class ProductUpdateViewModel(
    savedStateHandle: SavedStateHandle,
    private val productRepository: PruductRepository,
) : ViewModel() {
    var productUpdateUiState by mutableStateOf(ProductUpdateUiState())
        private set

    init {
        val productName = savedStateHandle.get<String>("productName") ?: ""
        val productDetails = productRepository.getProductStream(productName).filterNotNull().map {
            it.toProductDetails()
        }
        viewModelScope.launch {
            productDetails.collect {
                updateUiState(it)
            }
        }
    }

    suspend fun updateProduct() {
        if (validateInput()) {
            productRepository.updateProduct(productUpdateUiState.productDetails.toProduct())
        }
    }

    fun updateUiState(productDetails: ProductDetails) {
        productUpdateUiState = productUpdateUiState.copy(
            productDetails = productDetails,
            isSaveButtonEnabled = validateInput(productDetails)
        )
    }

    /**
     * Valida que el formulario no tenga campos vacíos
     */
    private fun validateInput(productDetails: ProductDetails = productUpdateUiState.productDetails): Boolean {
        return with(productDetails) {
            productName.isNotBlank() && productPrice.isNotBlank() && productQuantity.isNotBlank()
        }
    }
}