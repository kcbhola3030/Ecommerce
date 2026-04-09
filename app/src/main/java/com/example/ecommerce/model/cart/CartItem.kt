package com.example.ecommerce.model.cart

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItem(
    @PrimaryKey
    val product_id: String,
    val product_name: String,
    val product_image: String,
    val price: Double,
    var quantity: Int = 1
) {
    val totalPrice: Double
        get() = price * quantity
}