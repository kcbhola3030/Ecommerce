package com.example.ecommerce.model.cart

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface CartDao {

    @Query("SELECT * FROM cart_items")
    fun getAllItems(): LiveData<List<CartItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addItem(item: CartItem)

    @Update
    fun updateItem(item: CartItem)

    @Delete
    fun removeItem(item: CartItem)

    @Query("DELETE FROM cart_items")
    fun clearCart()

    @Query("SELECT * FROM cart_items WHERE product_id = :productId")
    fun getItemOnce(productId: String): CartItem?
}