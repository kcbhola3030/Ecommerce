package com.example.ecommerce.model.data

data class ProductListResponse(
    val status: Int,
    val message: String,
    val products: List<Product>?
)