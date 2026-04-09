package com.example.ecommerce.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.ecommerce.model.cart.CartItem
import com.example.ecommerce.model.repository.CartRepository

class CartViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = CartRepository(application)

    val cartItems: LiveData<List<CartItem>> = repository.allItems

    fun addToCart(item: CartItem) {
        Thread {
            val existing = repository.getItemOnce(item.product_id)
            if (existing != null) {
                existing.quantity += 1
                repository.updateItem(existing)
            } else {
                repository.addItem(item)
            }
        }.start()
    }
    fun increaseQuantity(item: CartItem) {
        item.quantity += 1
        repository.updateItem(item)
    }

    fun decreaseQuantity(item: CartItem) {
        if (item.quantity > 1) {
            item.quantity -= 1
            repository.updateItem(item)
        } else {
            repository.removeItem(item)
        }
    }

    fun removeItem(item: CartItem) {
        repository.removeItem(item)
    }

    fun clearCart() {
        repository.clearCart()
    }

    fun getTotalBill(items: List<CartItem>): Double {
        return items.sumOf { it.totalPrice }
    }
}