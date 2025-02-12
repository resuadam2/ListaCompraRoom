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

data class ProductDetailUiState(
    val productDetails: ProductDetails = ProductDetails(),
)

class ProductDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val productRepository: PruductRepository,
) : ViewModel() {
    var productDetailUiState by mutableStateOf(ProductDetailUiState())
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

    private fun updateUiState(productDetails: ProductDetails) {
        productDetailUiState = ProductDetailUiState(productDetails)
    }
}