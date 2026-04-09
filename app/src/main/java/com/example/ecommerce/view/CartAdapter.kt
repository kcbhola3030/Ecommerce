package com.example.ecommerce.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerce.databinding.ItemCartBinding
import com.example.ecommerce.model.cart.CartItem

class CartAdapter(
    private var cartItems: List<CartItem>,
    private val onIncrease: (CartItem) -> Unit,
    private val onDecrease: (CartItem) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(val binding: ItemCartBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CartItem) {
            binding.tvProductName.text = item.product_name
            binding.tvUnitPrice.text   = "$ ${item.price}"
            binding.tvTotalPrice.text  = "$ ${item.totalPrice}"
            binding.tvQuantity.text    = item.quantity.toString()

            val imageUrl = "http://103.163.198.93/myshop/images/${item.product_image}"
            Glide.with(binding.root.context)
                .load(imageUrl)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(binding.ivProduct)

            binding.tvIncrease.setOnClickListener { onIncrease(item) }
            binding.tvDecrease.setOnClickListener { onDecrease(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(cartItems[position])
    }

    override fun getItemCount() = cartItems.size

    fun updateList(newList: List<CartItem>) {
        cartItems = newList
        notifyDataSetChanged()
    }
}