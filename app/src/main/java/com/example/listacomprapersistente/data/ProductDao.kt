package com.example.listacomprapersistente.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM products")
    fun getAllProducts(): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE name = :name")
    fun getProductByName(name: String): Flow<Product>

    @Insert
    fun insertProduct(product: Product)

    @Update
    fun updateProduct(product: Product)

    @Delete
    fun deleteProduct(product: Product)
}