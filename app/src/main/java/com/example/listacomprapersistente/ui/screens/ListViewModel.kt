package com.example.listacomprapersistente.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listacomprapersistente.data.Product
import com.example.listacomprapersistente.data.PruductRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class ListUiState(
    val products: List<Product> = emptyList(),
    val totalQuantity: Int = 0,
    val totalPrice: Double = 0.0
)

class ListViewModel(private val productRepository: PruductRepository) : ViewModel() {

    /*
    Usamos un companion object para definir constantes que se utilizan en la clase.
    De esta forma, se pueden acceder a estas constantes sin necesidad de crear una instancia de la clase.
    TIMEOUT_MILLIS: Tiempo de espera en milisegundos para mantener el estado de la lista de productos.
    */
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    /*
    * Esta es la variable que se encarga de mantener el estado de la lista de productos,
    * En este caso se utiliza un StateFlow que se encarga de emitir el estado actual de la lista de productos
    * A diferencia de otros ejemplos, usamos el la función stateIn para mantener el estado
    * de la lista de productos, la diferencia es que al usar stateIn se puede especificar
    * un tiempo de espera para que el estado se mantenga, en este caso se especifica un tiempo de 5 segundos
    * para que el estado se mantenga, esto es útil para evitar que la lista de productos se actualice
    * constantemente, lo que puede ser molesto para el usuario
     */
    val listUiState: StateFlow<ListUiState> =
        productRepository.getProductsStream().map {
            ListUiState(it)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = ListUiState()
        )


    suspend fun deleteProduct(product: Product) {
        productRepository.deleteProduct(product)
        updateTotals()
    }

    private fun updateTotals() {
        val products = listUiState.value.products
        val totalQuantity = products.sumOf { it.quantity }
        val totalPrice = products.sumOf { it.price }
        listUiState.value.copy(totalQuantity = totalQuantity, totalPrice = totalPrice)
    }
}