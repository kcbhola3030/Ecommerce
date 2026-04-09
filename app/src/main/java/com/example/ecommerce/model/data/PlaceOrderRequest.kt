package com.example.ecommerce.model.data

data class PlaceOrderRequest(
    val user_id: String,
    val delivery_address: DeliveryAddress,
    val items: List<Item>,
    val bill_amount: Double,
    val payment_method: String
)