package com.example.listacomprapersistente.data

import kotlinx.coroutines.flow.Flow

interface PruductRepository {
    fun getAllProductsStream(): Flow<List<Product>>
    fun getProductStream(name: String): Flow<Product?>
    suspend fun insertProduct(product: Product)
    suspend fun updateProduct(product: Product)
    suspend fun deleteProduct(product: Product)
}

class PruductRepositoryImpl(private val productDao: ProductDao) : PruductRepository {
    override fun getAllProductsStream(): Flow<List<Product>> {
        return productDao.getAllProducts()
    }

    override fun getProductStream(name: String): Flow<Product?> {
        return productDao.getProductByName(name)
    }

    override suspend fun insertProduct(product: Product) {
        productDao.insertProduct(product)
    }

    override suspend fun updateProduct(product: Product) {
        productDao.updateProduct(product)
    }

    override suspend fun deleteProduct(product: Product) {
        productDao.deleteProduct(product)
    }
}