package com.example.ecommerce.model.network

import com.example.ecommerce.model.data.AddressListResponse
import com.example.ecommerce.model.data.AddressRequest
import com.example.ecommerce.model.data.AddressResponse
import com.example.ecommerce.model.data.CategoryResponse
import com.example.ecommerce.model.data.LoginRequest
import com.example.ecommerce.model.data.LoginResponse
import com.example.ecommerce.model.data.PlaceOrderRequest
import com.example.ecommerce.model.data.PlaceOrderResponse
import com.example.ecommerce.model.data.ProductDetailResponse
import com.example.ecommerce.model.data.ProductListResponse
import com.example.ecommerce.model.data.RegisterRequest
import com.example.ecommerce.model.data.RegisterResponse
import com.example.ecommerce.model.data.SearchResponse
import com.example.ecommerce.model.data.SubCategoryResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("User/auth")
    fun loginUser(
        @Body request: LoginRequest
    ): Call<LoginResponse>

    @POST("User/register")
    fun registerUser(
        @Body request: RegisterRequest
    ): Call<RegisterResponse>

    @GET("Category")
    fun getCategories(): Call<CategoryResponse>


    @GET("SubCategory")
    fun getSubCategories(
        @Query("category_id") categoryId: String
    ): Call<SubCategoryResponse>

    @GET("SubCategory/products/{sub_category_id}")
    fun getProductsBySubCategory(
        @Path("sub_category_id") subCategoryId: String
    ): Call<ProductListResponse>

    @GET("Product/details/{product_id}")
    fun getProductDetail(
        @Path("product_id") productId: String
    ): Call<ProductDetailResponse>

    @GET("Product/search")
    fun searchProduct(
        @Query("query") query: String
    ): Call<SearchResponse>

    @POST("User/address")
    fun addAddress(
        @Body request: AddressRequest
    ): Call<AddressResponse>

    @GET("User/addresses/{user_id}")
    fun getAddresses(
        @Path("user_id") userId: String
    ): Call<AddressListResponse>

    @POST("Order")
    fun placeOrder(
        @Body request: PlaceOrderRequest
    ): Call<PlaceOrderResponse>


}