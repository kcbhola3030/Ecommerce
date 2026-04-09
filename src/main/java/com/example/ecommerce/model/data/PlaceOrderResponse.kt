package com.example.ecommerce.model.data

data class PlaceOrderResponse(
    val status: Int,
    val message: String,
    val order_id: Int?
)