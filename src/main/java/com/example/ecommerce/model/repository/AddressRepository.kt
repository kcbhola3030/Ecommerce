package com.example.ecommerce.model.repository

import com.example.ecommerce.model.data.AddressListResponse
import com.example.ecommerce.model.data.AddressRequest
import com.example.ecommerce.model.data.AddressResponse
import com.example.ecommerce.model.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddressRepository {

    private val apiService = ApiClient.instance

    fun getAddresses(
        userId: String,
        onSuccess: (AddressListResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        apiService.getAddresses(userId).enqueue(object : Callback<AddressListResponse> {
            override fun onResponse(call: Call<AddressListResponse>, response: Response<AddressListResponse>) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    if (body.status == 0) onSuccess(body)
                    else onError(body.message)
                } else onError("Something went wrong.")
            }
            override fun onFailure(call: Call<AddressListResponse>, t: Throwable) {
                onError(t.message ?: "Network error.")
            }
        })
    }

    fun addAddress(
        request: AddressRequest,
        onSuccess: (AddressResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        apiService.addAddress(request).enqueue(object : Callback<AddressResponse> {
            override fun onResponse(call: Call<AddressResponse>, response: Response<AddressResponse>) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    if (body.status == 0) onSuccess(body)
                    else onError(body.message)
                } else onError("Something went wrong.")
            }
            override fun onFailure(call: Call<AddressResponse>, t: Throwable) {
                onError(t.message ?: "Network error.")
            }
        })
    }
}