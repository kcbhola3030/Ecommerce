package com.example.ecommerce.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ecommerce.model.data.LoginResponse
import com.example.ecommerce.model.data.RegisterResponse
import com.example.ecommerce.model.repository.AuthRepository

class AuthViewModel : ViewModel(){
    private val repository = AuthRepository()
    private val _loginSuccess = MutableLiveData< LoginResponse>()
    val loginSuccess: LiveData<LoginResponse> = _loginSuccess


    private val _loginError = MutableLiveData<String>()
    val loginError: LiveData<String> = _loginError

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _registerSuccess = MutableLiveData<RegisterResponse>()
    val registerSuccess: LiveData<RegisterResponse> = _registerSuccess

    private val _registerError = MutableLiveData<String>()
    val registerError: LiveData<String> = _registerError


    fun login(email: String, password: String) {

        // Basic validation
        if (email.isEmpty()) {
            _loginError.value = "Please enter your email"
            return
        }
        if (password.isEmpty()) {
            _loginError.value = "Please enter your password"
            return
        }

        _isLoading.value = true

        repository.login(
            email = email,
            password = password,
            onSuccess = { response ->
                _isLoading.value = false
                _loginSuccess.value = response
            },
            onError = { errorMessage ->
                _isLoading.value = false
                _loginError.value = errorMessage
            }
        )
    }

    fun register(
        fullName: String,
        mobileNo: String,
        email: String,
        password: String
    ) {
        if (fullName.isEmpty()) {
            _registerError.value = "Please enter your full name"
            return
        }
        if (mobileNo.isEmpty()) {
            _registerError.value = "Please enter your mobile number"
            return
        }
        if (email.isEmpty()) {
            _registerError.value = "Please enter your email"
            return
        }
        if (password.isEmpty()) {
            _registerError.value = "Please enter your password"
            return
        }

        _isLoading.value = true

        repository.register(
            fullName = fullName,
            mobileNo = mobileNo,
            email = email,
            password = password,
            onSuccess = { response ->
                _isLoading.value = false
                _registerSuccess.value = response
            },
            onError = { errorMessage ->
                _isLoading.value = false
                _registerError.value = errorMessage
            }
        )
    }


}