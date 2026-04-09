package com.example.ecommerce.model.data

data class CategoryResponse(
    val status: Int,
    val message: String,
    val categories: List<Category>


)