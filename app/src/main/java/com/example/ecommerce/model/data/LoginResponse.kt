package com.example.ecommerce.model.data

data class LoginResponse(
    val message: String,
    val status: Int,
    val user: User?
)