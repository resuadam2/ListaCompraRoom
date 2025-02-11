package com.example.listacomprapersistente.ui.screens

import androidx.activity.result.launch
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listacomprapersistente.data.Product
import com.example.listacomprapersistente.data.PruductRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

data class ListUiState(
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = true,
    val totalQuantity: Int = 0,
    val totalPrice: Double = 0.0,
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

    private val _listUiState = MutableStateFlow(ListUiState())
    val listUiState: StateFlow<ListUiState> = _listUiState.asStateFlow()

    init {
        viewModelScope.launch {
            productRepository.getAllProductsStream().onEach { products ->
                _listUiState.value = _listUiState.value.copy(
                    products = products,
                    isLoading = false
                )
                calculateTotals()
            }.collect {}
        }    }

    suspend fun deleteProduct(product: Product) {
        productRepository.deleteProduct(product)
        delay(500) // Pequeño delay para que de tiempo a borrar de bd
        calculateTotals()
    }

    private fun calculateTotals() {
        val products = _listUiState.value.products
        var quantity = 0
        var price = 0.0
        products.forEach {
            quantity += it.quantity
            price += it.price * it.quantity
        }
        _listUiState.value = _listUiState.value.copy(
            totalQuantity = quantity,
            totalPrice = price
        )
    }
}