package com.example.ecommerce.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ecommerce.model.data.Category
import com.example.ecommerce.model.data.Product
import com.example.ecommerce.model.data.ProductX
import com.example.ecommerce.model.data.Subcategory
import com.example.ecommerce.model.repository.ProductRepository

class ProductViewModel: ViewModel() {
    private val repository = ProductRepository()

    private val _categories = MutableLiveData<List<Category>>()
    val categories:  LiveData<List<Category>> = _categories

    private val _subCategories = MutableLiveData<List<Subcategory>>()
    val subCategories: LiveData<List<Subcategory>> = _subCategories

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    private val _productDetail = MutableLiveData<ProductX>()
    val productDetail: LiveData<ProductX> = _productDetail

    private val _searchResults = MutableLiveData<List<Product>>()
    val searchResults: LiveData<List<Product>> = _searchResults

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun getCategories()
    {
        _isLoading.value=true

        repository.getCategories(
            onSuccess = { response->
                _isLoading.value = false
                _categories.postValue( response.categories)

            },
            onError = {
                errorMessage->
                _isLoading.value = false
                _error.postValue(errorMessage)
            }
        )
    }

    fun getSubCategories(categoryId: String) {
        _isLoading.postValue(true)
        repository.getSubCategories(categoryId,
            onSuccess = {
                _isLoading.postValue(false)
                _subCategories.postValue(it.subcategories ?: emptyList())
            },
            onError = {
                _isLoading.postValue(false)
                _error.postValue(it)
            }
        )
    }

    fun getProductsBySubCategory(subCategoryId: String) {
        _isLoading.postValue(true)
        repository.getProductsBySubCategory(subCategoryId,
            onSuccess = {
                _isLoading.postValue(false)
                _products.postValue(it.products ?: emptyList())
            },
            onError = {
                _isLoading.postValue(false)
                _error.postValue(it)
            }
        )
    }

    fun getProductDetail(productId: String) {
        _isLoading.postValue(true)
        repository.getProductDetail(productId,
            onSuccess = {
                _isLoading.postValue(false)
                _productDetail.postValue(it.product!!)
            },
            onError = {
                _isLoading.postValue(false)
                _error.postValue(it)
            }
        )
    }



    fun searchProduct(query: String) {
        if (query.isEmpty()) {
            _error.postValue("Please enter search text")
            return
        }
        _isLoading.postValue(true)
        repository.searchProduct(query,
            onSuccess = {
                _isLoading.postValue(false)
                _searchResults.postValue(it.products ?: emptyList())
            },
            onError = {
                _isLoading.postValue(false)
                _error.postValue(it)
            }
        )
    }

}