package com.example.ecommerce.model.repository

import com.example.ecommerce.model.data.PlaceOrderRequest
import com.example.ecommerce.model.data.PlaceOrderResponse
import com.example.ecommerce.model.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderRepository {

    private val apiService = ApiClient.instance

    fun placeOrder(
        request: PlaceOrderRequest,
        onSuccess: (PlaceOrderResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        apiService.placeOrder(request).enqueue(object : Callback<PlaceOrderResponse> {
            override fun onResponse(
                call: Call<PlaceOrderResponse>,
                response: Response<PlaceOrderResponse>
            ) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    if (body.status == 0) onSuccess(body)
                    else onError(body.message)
                } else onError("Something went wrong.")
            }
            override fun onFailure(call: Call<PlaceOrderResponse>, t: Throwable) {
                onError(t.message ?: "Network error.")
            }
        })
    }
}