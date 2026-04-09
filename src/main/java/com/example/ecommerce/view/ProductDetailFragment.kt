package com.example.ecommerce.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.ecommerce.databinding.FragmentProductDetailBinding
import com.example.ecommerce.model.cart.CartItem
import com.example.ecommerce.viewmodel.CartViewModel
import com.example.ecommerce.viewmodel.ProductViewModel

class ProductDetailFragment : Fragment() {

    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ProductViewModel

    private lateinit var cartViewModel: CartViewModel
    private lateinit var productAdapter: ProductAdapter

    private val args: ProductDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        cartViewModel = ViewModelProvider(requireActivity()).get(CartViewModel::class.java)

        observeViewModel()

        viewModel.getProductDetail(args.productId)
    }

    private fun observeViewModel() {
        viewModel.productDetail.observe(viewLifecycleOwner) { product ->

            binding.tvProductName.text  = product.product_name
            binding.tvPrice.text        = "$ ${product.price}"
            binding.tvDescription.text  = product.description

            val imageUrl = "http://103.163.198.93/myshop/images/${product.product_image_url}"
            Glide.with(requireContext())
                .load(imageUrl)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(binding.ivProduct)

            android.util.Log.d("ProductDetail", "Reviews: ${product.reviews}")
            android.util.Log.d("ProductDetail", "Reviews size: ${product.reviews?.size}")
            product.specifications?.let { specs ->
                binding.rvSpecifications.apply {
                    layoutManager = LinearLayoutManager(requireContext())
                    adapter = SpecificationAdapter(specs)
                }
            }

            product.reviews?.let { reviews ->
                binding.rvReviews.apply {
                    layoutManager = LinearLayoutManager(requireContext())
                    adapter = ReviewAdapter(reviews)
                }
            }

//            binding.tvAddToCart.setOnClickListener {
//                val cartItem = com.example.ecommerce.model.cart.CartItem(
//                    product_id    = product.product_id,
//                    product_name  = product.product_name,
//                    product_image = product.product_image_url,
//                    price         = product.price.toDoubleOrNull() ?: 0.0,
//                    quantity      = 1
//                )
//                cartViewModel.addToCart(cartItem)
//                Toast.makeText(requireContext(), "Added to cart!", Toast.LENGTH_SHORT).show()
//            }


            binding.tvAddToCart.setOnClickListener {
                val cartItem = CartItem(
                    product_id    = product.product_id,
                    product_name  = product.product_name,
                    product_image = product.product_image_url,
                    price         = product.price.toDoubleOrNull() ?: 0.0,
                    quantity      = 1
                )
                cartViewModel.addToCart(cartItem)
            }
            binding.tvIncrease.setOnClickListener {
                cartViewModel.cartItems.value
                    ?.find { it.product_id == product.product_id }
                    ?.let { cartViewModel.increaseQuantity(it) }
            }

            binding.tvDecrease.setOnClickListener {
                cartViewModel.cartItems.value
                    ?.find { it.product_id == product.product_id }
                    ?.let { cartViewModel.decreaseQuantity(it) }
            }
        }
            cartViewModel.cartItems.observe(viewLifecycleOwner) { cartItems ->
                val productId = args.productId
                val cartItem = cartItems.find { it.product_id == productId }

                if (cartItem != null) {
                    binding.tvAddToCart.visibility    = View.GONE
                    binding.layoutQuantity.visibility = View.VISIBLE
                    binding.tvQuantity.text           = cartItem.quantity.toString()
                } else {
                    binding.tvAddToCart.visibility    = View.VISIBLE
                    binding.layoutQuantity.visibility = View.GONE
                }
            }



        viewModel.error.observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}