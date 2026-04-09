package com.example.ecommerce.view

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerce.R
import com.example.ecommerce.databinding.FragmentHomeBinding
import com.example.ecommerce.model.cart.CartItem
import com.example.ecommerce.model.data.Product
import com.example.ecommerce.viewmodel.CartViewModel
import com.example.ecommerce.viewmodel.ProductViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ProductViewModel
    private lateinit var cartViewModel: CartViewModel
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var searchAdapter: ProductAdapter

    private var isSearchVisible = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        cartViewModel = ViewModelProvider(requireActivity()).get(CartViewModel::class.java)
        setupRecyclerView()
        setupSearch()
        setupSearchToggle()
        observeViewModel()

        viewModel.getCategories()
    }
    private fun setupSearchToggle() {
        val ivSearch = requireActivity().findViewById<ImageView>(R.id.iv_search)
        ivSearch?.setOnClickListener {
            isSearchVisible = !isSearchVisible
            if (isSearchVisible) {
                binding.layoutSearch.visibility = View.VISIBLE
                binding.etSearch.requestFocus()
            } else {
                binding.layoutSearch.visibility = View.GONE
                binding.etSearch.text?.clear()
                binding.tvCancel.visibility = View.GONE
                showCategories()
            }
        }}

    private fun setupRecyclerView() {
        categoryAdapter = CategoryAdapter(emptyList()) { category ->
            val action = HomeFragmentDirections
                .actionHomeToSubcategory(
                    categoryId = category.category_id,
                    categoryName = category.category_name
                )
            findNavController().navigate(action)
        }
        binding.rvCategories.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = categoryAdapter
        }

        searchAdapter = ProductAdapter(
            products         = emptyList(),
            cartItems        = emptyList(),
            onProductClick   = { product ->
                val action = HomeFragmentDirections
                    .actionHomeToProductDetail(
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
            onIncreaseClick  = { cartItem ->
                cartViewModel.increaseQuantity(cartItem)
            },
            onDecreaseClick  = { cartItem ->
                cartViewModel.decreaseQuantity(cartItem)
            }
        )
        binding.rvSearchResults.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = searchAdapter
        }
    }

    private fun setupSearch() {

        binding.etSearch.setOnFocusChangeListener { _, hasFocus ->
            binding.tvCancel.visibility =
                if (hasFocus) View.VISIBLE else View.GONE
        }

        binding.etSearch.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: android.text.Editable?) {
                val query = s.toString().trim()
                if (query.length >= 3) {
                    android.util.Log.d("Search", "Searching for: $query")
                    viewModel.searchProduct(query)
                }
                if (query.isEmpty()) {
                    showCategories()
                }
            }
        })

        binding.etSearch.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                event?.keyCode == KeyEvent.KEYCODE_ENTER) {
                val query = binding.etSearch.text.toString().trim()
                android.util.Log.d("Search", "Keyboard search: $query")
                viewModel.searchProduct(query)
                true
            } else false
        }

        binding.tvCancel.setOnClickListener {
            binding.etSearch.text?.clear()
            binding.etSearch.clearFocus()
            showCategories()
        }
    }
    private fun observeViewModel() {
        viewModel.categories.observe(viewLifecycleOwner) { categories ->
            categoryAdapter.updateList(categories)
        }

        viewModel.searchResults.observe(viewLifecycleOwner) { products ->
            showSearchResults(products)
        }

        cartViewModel.cartItems.observe(viewLifecycleOwner) { cartItems ->
            searchAdapter.updateCartItems(cartItems)
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showSearchResults(products: List<Product>) {
        binding.rvCategories.visibility = View.GONE
        binding.rvSearchResults.visibility = View.VISIBLE
        searchAdapter.updateList(products)
    }

    private fun showCategories() {
        binding.rvCategories.visibility = View.VISIBLE
        binding.rvSearchResults.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}