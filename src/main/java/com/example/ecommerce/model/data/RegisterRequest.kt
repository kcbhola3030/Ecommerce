package com.example.ecommerce.model.data

data class RegisterRequest(
    val full_name: String,
    val mobile_no: String,
    val email_id: String,
    val password: String
)