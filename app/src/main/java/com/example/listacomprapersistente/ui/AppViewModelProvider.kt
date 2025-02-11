package com.example.listacomprapersistente.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.listacomprapersistente.ListaCompraApplication
import com.example.listacomprapersistente.ui.screens.ListViewModel
import com.example.listacomprapersistente.ui.screens.ProductAddViewModel
import com.example.listacomprapersistente.ui.screens.ProductDetailViewModel
import com.example.listacomprapersistente.ui.screens.ProductUpdateViewModel


/**
 * El objeto AppViewModelProvider contiene un factory para crear instancias de ViewModels.
 * Esto nos sirve para poder crear instancias de ViewModels que necesiten dependencias.
 * En este caso, el factory se encarga de crear instancias de ListViewModel.
 * Un Provider es un objeto que se encarga de proveer instancias de objetos.
 * En este caso, el AppViewModelProvider provee instancias de ViewModels.
 * Necesitamos las instancias de ViewModels para poder utilizarlas en las pantallas de la aplicación
 * de forma que no tengamos que preocuparnos por la creación de las instancias de los ViewModels en
 * cada pantalla y siempre sean las mismas instancias.
 */

object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer para ListViewModel
        initializer {
            ListViewModel(listaCompraApp().appContainer.productRepository)
        }
        // Initializer para ProductAddViewModel
        initializer {
            ProductAddViewModel(listaCompraApp().appContainer.productRepository)
        }
        // Initializer para ProductDetailViewModel
        initializer {
            ProductDetailViewModel(
                this.createSavedStateHandle(),
                listaCompraApp().appContainer.productRepository)
        }
        // Initializer para ProductUpdateViewModel
        initializer {
            ProductUpdateViewModel(
            this.createSavedStateHandle(),
            listaCompraApp().appContainer.productRepository
            )
        }
    }
}

/**
 * Extension function para obtener la instancia de la aplicación desde el ViewModelProvider.
 * Esto nos sirve para obtener la instancia de la aplicación en cualquier parte del código.
 * De esta forma podemos acceder a los componentes de la aplicación desde cualquier parte.
 */
fun CreationExtras.listaCompraApp(): ListaCompraApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ListaCompraApplication)