package com.example.ecommerce.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ecommerce.model.cart.CartItem
import com.example.ecommerce.model.data.Address
import com.example.ecommerce.model.data.DeliveryAddress
import com.example.ecommerce.model.data.Item
import com.example.ecommerce.model.data.PlaceOrderRequest
import com.example.ecommerce.model.data.PlaceOrderResponse
import com.example.ecommerce.model.repository.OrderRepository

class OrderViewModel : ViewModel() {

    private val repository = OrderRepository()

    private val _orderSuccess = MutableLiveData<PlaceOrderResponse?>()
    val orderSuccess: LiveData<PlaceOrderResponse?> = _orderSuccess

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun resetOrderSuccess() {
        _orderSuccess.value = null
    }

    fun placeOrder(
        userId: String,
        address: Address,
        cartItems: List<CartItem>,
        billAmount: Double,
        paymentMethod: String
    ) {
        _isLoading.postValue(true)

        // Build request
        val orderItems = cartItems.map { item ->
            Item(
                product_id = item.product_id.toInt(),
                quantity   = item.quantity,
                unit_price = item.price.toInt()
            )
        }

        val deliveryAddress = DeliveryAddress(
            title   = address.title,
            address = address.address
        )

        val request = PlaceOrderRequest(
            user_id          = userId,
            delivery_address = deliveryAddress,
            items            = orderItems,
            bill_amount      = billAmount,
            payment_method   = paymentMethod
        )

        repository.placeOrder(request,
            onSuccess = {
                _isLoading.postValue(false)
                _orderSuccess.postValue(it)
            },
            onError = {
                _isLoading.postValue(false)
                _error.postValue(it)
            }
        )
        fun resetOrderSuccess() {
            _orderSuccess.postValue(null)
        }
    }
}