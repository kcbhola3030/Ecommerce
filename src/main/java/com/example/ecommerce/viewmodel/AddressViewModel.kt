package com.example.ecommerce.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ecommerce.model.data.Address
import com.example.ecommerce.model.data.AddressRequest
import com.example.ecommerce.model.repository.AddressRepository

class AddressViewModel : ViewModel() {

    private val repository = AddressRepository()

    private val _addresses = MutableLiveData<List<Address>>()
    val addresses: LiveData<List<Address>> = _addresses

    private val _addSuccess = MutableLiveData<String>()
    val addSuccess: LiveData<String> = _addSuccess

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun getAddresses(userId: String) {
        repository.getAddresses(userId,
            onSuccess = {
                _addresses.postValue(it.addresses ?: emptyList())
            },
            onError = {
                _error.postValue(it)
            }
        )
    }

    fun addAddress(userId: String, title: String, address: String) {
        if (title.isEmpty() || address.isEmpty()) {
            _error.postValue("Please fill all fields")
            return
        }
        val request = AddressRequest(userId, title, address)
        repository.addAddress(request,
            onSuccess = {
                _addSuccess.postValue(it.message)
                getAddresses(userId)
            },
            onError = {
                _error.postValue(it)
            }
        )
    }
}