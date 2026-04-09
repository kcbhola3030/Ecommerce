package com.example.ecommerce.model.repository

import com.example.ecommerce.model.data.LoginRequest
import com.example.ecommerce.model.data.LoginResponse
import com.example.ecommerce.model.data.RegisterRequest
import com.example.ecommerce.model.data.RegisterResponse
import com.example.ecommerce.model.network.ApiClient
import okhttp3.Callback
import retrofit2.Call
import retrofit2.Response

class AuthRepository{
    private val apiService = ApiClient.instance

    fun login(
        email: String,
        password: String,
        onSuccess: (LoginResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        val request = LoginRequest(email, password)

        apiService.loginUser(request).enqueue(object : retrofit2.Callback<LoginResponse> {


            override fun onResponse(
                call: Call<LoginResponse?>,
                response: Response<LoginResponse?>
            ) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    if (body.status == 0) {
                        onSuccess(body)
                    } else {
                        onError(body.message)
                    }
                } else {
                    onError("Something went wrong. Try again.")
                }
            }

            override fun onFailure(
                call: Call<LoginResponse?>,
                t: Throwable
            ) {
                onError(t.message ?: "Network error. Check your connection.")
            }
        })
    }

    fun register(
        fullName: String,
        mobileNo: String,
        email: String,
        password: String,
        onSuccess: (RegisterResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        val request = RegisterRequest(fullName, mobileNo, email, password)

        apiService.registerUser(request).enqueue(object : retrofit2.Callback<RegisterResponse> {

            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    if (body.status == 0) {
                        onSuccess(body)
                    } else {
                        onError(body.message)
                    }
                } else {
                    onError("Something went wrong. Try again.")
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                onError(t.message ?: "Network error. Check your connection.")
            }
        })
    }
}