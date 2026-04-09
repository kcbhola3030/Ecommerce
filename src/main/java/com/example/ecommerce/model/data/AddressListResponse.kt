package com.example.ecommerce.model.data

data class AddressListResponse(
    val status: Int,
    val message: String,
    val addresses: List<Address>?
)