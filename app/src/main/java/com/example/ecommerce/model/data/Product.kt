package com.example.ecommerce.model.data

data class Product(
    val product_id: String,
    val product_name: String,
    val description: String,
    val category_id: String,
    val category_name: String,
    val sub_category_id: String,
    val subcategory_name: String,
    val price: String,
    val average_rating: String,
    val product_image_url: String
)