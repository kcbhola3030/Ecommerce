package com.example.ecommerce.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecommerce.databinding.ItemProductBinding
import com.example.ecommerce.model.cart.CartItem
import com.example.ecommerce.model.data.Product

class ProductAdapter(private var products: List<Product>,
                     private var cartItems: List<CartItem> = emptyList(),
                     private val onProductClick: (Product) -> Unit,
                     private val onAddToCartClick: (Product) -> Unit,
                     private val onIncreaseClick: (CartItem) -> Unit,
                     private val onDecreaseClick: (CartItem) -> Unit
    )


    :  RecyclerView.Adapter<ProductAdapter.ProductViewHolder>()  {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {
        val binding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int
    ) {
        holder.bind(products[position])
    }

    override fun getItemCount()= products.size

    fun updateList(newList: List<Product>) {
        products = newList
        notifyDataSetChanged()
    }
    fun updateCartItems(newCartItems: List<CartItem>) {
        cartItems = newCartItems
        notifyDataSetChanged()
    }


    inner class ProductViewHolder(val binding: ItemProductBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.tvProductName.text  = product.product_name
            binding.tvDescription.text  = product.description
            binding.tvPrice.text        = "$ ${product.price}"

            val imageUrl = "http://103.163.198.93/myshop/images/${product.product_image_url}"
            Glide.with(binding.root.context)
                .load(imageUrl)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_menu_report_image)
                .into(binding.ivProduct)

            val cartItem = cartItems.find { it.product_id == product.product_id }

            if (cartItem != null) {
                binding.tvAddToCart.visibility   = View.GONE
                binding.layoutQuantity.visibility = View.VISIBLE
                binding.tvQuantity.text           = cartItem.quantity.toString()

                binding.tvIncrease.setOnClickListener { onIncreaseClick(cartItem) }
                binding.tvDecrease.setOnClickListener { onDecreaseClick(cartItem) }

            } else {
                binding.tvAddToCart.visibility   = View.VISIBLE
                binding.layoutQuantity.visibility = View.GONE

                binding.tvAddToCart.setOnClickListener {
                    onAddToCartClick(product)
                }
            }

            binding.root.setOnClickListener {
                onProductClick(product)
            }

        }
    }




}