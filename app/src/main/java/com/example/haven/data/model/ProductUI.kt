package com.example.haven.data.model

data class ProductUI(
    val category: String,
    val count: Int,
    val description: String,
    val id: Int,
    val imageOne: String,
    val imageThree: String,
    val imageTwo: String,
    val price: Double,
    val rate: Double,
    val saleState: Boolean,
    val salePrice: Double,
    val title: String,
)
