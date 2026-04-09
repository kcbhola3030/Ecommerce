package com.example.ecommerce.model.data

data class SubCategoryResponse(
    val status: Int,
    val message: String,
    val subcategories: List<Subcategory>?
)