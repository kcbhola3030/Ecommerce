package com.example.ecommerce.model.data

data class ProductX(
    val product_id: String,
    val product_name: String,
    val description: String,
    val category_id: String,
    val sub_category_id: String,
    val price: String,
    val average_rating: String,
    val product_image_url: String,
    val is_active: String,
    val images: List<Image>?,
    val specifications: List<Specification>?,
    val reviews: List<Review>?
)