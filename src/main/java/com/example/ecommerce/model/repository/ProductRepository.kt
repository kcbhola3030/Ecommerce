package com.example.ecommerce.model.repository

import com.example.ecommerce.model.data.CategoryResponse
import com.example.ecommerce.model.data.ProductDetailResponse
import com.example.ecommerce.model.data.ProductListResponse
import com.example.ecommerce.model.data.SearchResponse
import com.example.ecommerce.model.data.SubCategoryResponse
import com.example.ecommerce.model.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductRepository {
    private val apiService = ApiClient.instance

    fun getCategories(
        onSuccess:(CategoryResponse)->Unit,
        onError: (String)->Unit
    ){
        apiService.getCategories().enqueue(object: Callback<CategoryResponse>
        {
            override fun onResponse(
                call: Call<CategoryResponse?>,
                response: Response<CategoryResponse?>
            ) {
                val body = response.body()

                if(response.isSuccessful && body!= null)
                {
                    if(body.status==0)
                    {
                        onSuccess(body)
                    }
                    else{
                        onError(body.message)
                    }
                }
                else{
                    onError("Something went wrong. Try again.")
                }
            }

            override fun onFailure(
                call: Call<CategoryResponse?>,
                t: Throwable
            ) {
                onError(t.message?:"Error")
            }

        })
    }

    fun getSubCategories(
        categoryId: String,
        onSuccess: (SubCategoryResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        apiService.getSubCategories(categoryId).enqueue(object : Callback<SubCategoryResponse> {
            override fun onResponse(call: Call<SubCategoryResponse>, response: Response<SubCategoryResponse>) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    if (body.status == 0) onSuccess(body)
                    else onError(body.message)
                } else onError("Something went wrong.")
            }
            override fun onFailure(call: Call<SubCategoryResponse>, t: Throwable) {
                onError(t.message ?: "Network error.")
            }
        })
    }

    fun getProductsBySubCategory(
        subCategoryId: String,
        onSuccess: (ProductListResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        apiService.getProductsBySubCategory(subCategoryId).enqueue(object : Callback<ProductListResponse> {
            override fun onResponse(call: Call<ProductListResponse>, response: Response<ProductListResponse>) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    if (body.status == 0) onSuccess(body)
                    else onError(body.message)
                } else onError("Something went wrong.")
            }
            override fun onFailure(call: Call<ProductListResponse>, t: Throwable) {
                onError(t.message ?: "Network error.")
            }
        })
    }

    fun getProductDetail(
        productId: String,
        onSuccess: (ProductDetailResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        apiService.getProductDetail(productId).enqueue(object : Callback<ProductDetailResponse> {
            override fun onResponse(call: Call<ProductDetailResponse>, response: Response<ProductDetailResponse>) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    if (body.status == 0) onSuccess(body)
                    else onError(body.message)
                } else onError("Something went wrong.")
            }
            override fun onFailure(call: Call<ProductDetailResponse>, t: Throwable) {
                onError(t.message ?: "Network error.")
            }
        })
    }


    fun searchProduct(
        query: String,
        onSuccess: (SearchResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        apiService.searchProduct(query).enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    if (body.status == 0) onSuccess(body)
                    else onError(body.message)
                } else onError("Something went wrong.")
            }
            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                onError(t.message ?: "Network error.")
            }
        })
    }
}