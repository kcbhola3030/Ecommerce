package com.example.ecommerce.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerce.R
import com.example.ecommerce.databinding.FragmentSubCategoryBinding
import com.example.ecommerce.model.cart.CartItem
import com.example.ecommerce.model.data.Subcategory
import com.example.ecommerce.viewmodel.CartViewModel
import com.example.ecommerce.viewmodel.ProductViewModel
import com.google.android.material.tabs.TabLayout

class SubCategoryFragment : Fragment() {
    private var _binding: FragmentSubCategoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ProductViewModel

    private lateinit var cartViewModel: CartViewModel
    private lateinit var productAdapter: ProductAdapter

    private val args: SubCategoryFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSubCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProductViewModel::class.java)

        cartViewModel = ViewModelProvider(requireActivity()).get(CartViewModel::class.java)

        setupRecyclerView()
        observeViewModel()

        viewModel.getSubCategories(args.categoryId)
    }


    private fun setupRecyclerView() {
        productAdapter = ProductAdapter(
            products      = emptyList(),
            cartItems     = emptyList(),
            onProductClick = { product ->
                val action = SubCategoryFragmentDirections
                    .actionSubcategoryToDetail(
                        productId   = product.product_id,
                        productName = product.product_name
                    )
                findNavController().navigate(action)
            },
            onAddToCartClick = { product ->
                val cartItem = CartItem(
                    product_id    = product.product_id,
                    product_name  = product.product_name,
                    product_image = product.product_image_url,
                    price         = product.price.toDoubleOrNull() ?: 0.0,
                    quantity      = 1
                )
                cartViewModel.addToCart(cartItem)
            },
            onIncreaseClick = { cartItem ->
                cartViewModel.increaseQuantity(cartItem)
            },
            onDecreaseClick = { cartItem ->
                cartViewModel.decreaseQuantity(cartItem)
            }
        )

        binding.rvProducts.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = productAdapter
        }
    }
    private fun observeViewModel() {

        viewModel.subCategories.observe(viewLifecycleOwner) { subCategories ->
            setupTabs(subCategories)
        }

        viewModel.products.observe(viewLifecycleOwner) { products ->
            productAdapter.updateList(products)
        }

        cartViewModel.cartItems.observe(viewLifecycleOwner) { cartItems ->
            productAdapter.updateCartItems(cartItems)
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupTabs(subCategories: List<Subcategory>) {

        binding.tabLayout.removeAllTabs()

        subCategories.forEach { subCategory ->
            binding.tabLayout.addTab(
                binding.tabLayout.newTab().setText(subCategory.subcategory_name)
            )
        }

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val selected = subCategories[tab?.position ?: 0]
                viewModel.getProductsBySubCategory(selected.subcategory_id)
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        if (subCategories.isNotEmpty()) {
            viewModel.getProductsBySubCategory(subCategories[0].subcategory_id)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}