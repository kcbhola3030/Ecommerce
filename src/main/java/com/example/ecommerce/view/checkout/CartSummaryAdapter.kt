package com.example.ecommerce.view.checkout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerce.databinding.ItemCartSummaryBinding
import com.example.ecommerce.model.cart.CartItem

class CartSummaryAdapter(
    private val items: List<CartItem>
) : RecyclerView.Adapter<CartSummaryAdapter.SummaryViewHolder>() {

    inner class SummaryViewHolder(val binding: ItemCartSummaryBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CartItem) {
            binding.tvProductName.text = item.product_name
            binding.tvUnitPrice.text   = "$ ${item.price}"
            binding.tvQuantity.text    = item.quantity.toString()
            binding.tvAmount.text      = "$ ${item.totalPrice}"

            Glide.with(binding.root.context)
                .load("http://103.163.198.93/myshop/images/${item.product_image}")
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(binding.ivProduct)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SummaryViewHolder {
        val binding = ItemCartSummaryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SummaryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SummaryViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}