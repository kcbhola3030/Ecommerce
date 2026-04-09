package com.example.ecommerce.view.checkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerce.databinding.FragmentCartItemBinding
import com.example.ecommerce.viewmodel.CartViewModel

class CartItemsFragment : Fragment() {

    private var _binding: FragmentCartItemBinding? = null
    private val binding get() = _binding!!
    private lateinit var cartViewModel: CartViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cartViewModel = ViewModelProvider(requireActivity()).get(CartViewModel::class.java)

        observeViewModel()

        binding.btnNext.setOnClickListener {
            (parentFragment as CheckoutFragment).moveToNextTab()
        }
    }

    private fun observeViewModel() {
        cartViewModel.cartItems.observe(viewLifecycleOwner) { items ->
            binding.rvCartItems.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = CartSummaryAdapter(items)
            }

            val total = cartViewModel.getTotalBill(items)
            binding.tvTotal.text = "$ $total"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}