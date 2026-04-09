package com.example.ecommerce.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ecommerce.model.data.Address

class CheckoutViewModel : ViewModel() {

    private val _deliveryAddress = MutableLiveData<Address>()
    val deliveryAddress: LiveData<Address> = _deliveryAddress

    private val _paymentMethod = MutableLiveData<String>()
    val paymentMethod: LiveData<String> = _paymentMethod

    fun setDeliveryAddress(address: Address) {
        _deliveryAddress.value = address
    }

    fun setPaymentMethod(method: String) {
        _paymentMethod.value = method
    }
}