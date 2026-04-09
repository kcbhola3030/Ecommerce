package com.example.ecommerce.model.data

data class SearchResponse(
    val message: String,
    val products: List<Product>?,
    val status: Int
)