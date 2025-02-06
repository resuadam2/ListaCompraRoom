package com.example.listacomprapersistente.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey val name: String,
    val quantity: Int,
    val price: Double
)