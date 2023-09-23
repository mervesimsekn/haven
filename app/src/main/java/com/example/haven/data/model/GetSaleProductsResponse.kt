package com.example.haven.data.model

data class GetSaleProductsResponse(
    val message: String?,
    val products: List<Product>?,
    val status: Int?
)