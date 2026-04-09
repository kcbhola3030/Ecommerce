package com.example.ecommerce.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerce.databinding.FragmentCartBinding
import com.example.ecommerce.viewmodel.CartViewModel
import com.example.ecommerce.R

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private lateinit var cartViewModel: CartViewModel
    private lateinit var cartAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cartViewModel = ViewModelProvider(requireActivity()).get(CartViewModel::class.java)

        setupRecyclerView()
        observeViewModel()

        binding.btnCheckout.setOnClickListener {
            findNavController().navigate(R.id.checkoutFragment)
        }
    }

    private fun setupRecyclerView() {
        cartAdapter = CartAdapter(
            cartItems  = emptyList(),
            onIncrease = { item -> cartViewModel.increaseQuantity(item) },
            onDecrease = { item -> cartViewModel.decreaseQuantity(item) }
        )
        binding.rvCartItems.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = cartAdapter
        }
    }

    private fun observeViewModel() {
        cartViewModel.cartItems.observe(viewLifecycleOwner) { items ->
            if (items.isEmpty()) {
                binding.rvCartItems.visibility = View.GONE
                binding.tvEmpty.visibility     = View.VISIBLE
                binding.btnCheckout.isEnabled  = false
                binding.tvTotal.text           = "$ 0"
            } else {
                binding.rvCartItems.visibility = View.VISIBLE
                binding.tvEmpty.visibility     = View.GONE
                binding.btnCheckout.isEnabled  = true
                cartAdapter.updateList(items)

                val total = cartViewModel.getTotalBill(items)
                binding.tvTotal.text = "$ $total"
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}