package com.example.listacomprapersistente.data

import android.content.Context

class AppContainer(context: Context) {
    private val appDatabase = AppDatabase.getDatabase(context)
    private val productDao = appDatabase.productDao()
    val productRepository = PruductRepositoryImpl(productDao)

    fun provideProductRepository(): PruductRepository {
        return productRepository
    }
}