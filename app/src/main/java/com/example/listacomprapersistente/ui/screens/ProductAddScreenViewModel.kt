package com.example.listacomprapersistente.ui.screens

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.listacomprapersistente.data.PruductRepository

data class ProductAddScreenUiState(
    val action: String = "Add",
    val productName: String = "",
    val productPrice: String = "",
    val productQuantity: String = "",
)

class ProductAddScreenViewModel (
    savedStateHandle: SavedStateHandle,
    private val productRepository: PruductRepository,
) : ViewModel() {

}


