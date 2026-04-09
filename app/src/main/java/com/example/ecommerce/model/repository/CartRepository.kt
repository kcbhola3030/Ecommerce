package com.example.ecommerce.model.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.ecommerce.model.cart.CartDao
import com.example.ecommerce.model.cart.CartDatabase
import com.example.ecommerce.model.cart.CartItem

class CartRepository(context: Context) {

    private val cartDao: CartDao = CartDatabase.getDatabase(context).cartDao()

    val allItems: LiveData<List<CartItem>> = cartDao.getAllItems()

    fun addItem(item: CartItem) {
        Thread { cartDao.addItem(item) }.start()
    }

    fun updateItem(item: CartItem) {
        Thread { cartDao.updateItem(item) }.start()
    }

    fun removeItem(item: CartItem) {
        Thread { cartDao.removeItem(item) }.start()
    }

    fun clearCart() {
        Thread { cartDao.clearCart() }.start()
    }

    fun getItemOnce(productId: String): CartItem? {
        return cartDao.getItemOnce(productId)
    }
}