package com.example.ecommerce.view.checkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ecommerce.R
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerce.databinding.FragmentSummaryBinding
import com.example.ecommerce.viewmodel.CartViewModel
import com.example.ecommerce.viewmodel.CheckoutViewModel
import com.example.ecommerce.viewmodel.OrderViewModel

class SummaryFragment : Fragment() {

    private var _binding: FragmentSummaryBinding? = null
    private val binding get() = _binding!!

    private lateinit var cartViewModel: CartViewModel
    private lateinit var checkoutViewModel: CheckoutViewModel
    private lateinit var orderViewModel: OrderViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSummaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cartViewModel     = ViewModelProvider(requireActivity()).get(CartViewModel::class.java)
        checkoutViewModel = ViewModelProvider(requireActivity()).get(CheckoutViewModel::class.java)
        orderViewModel    = ViewModelProvider(requireActivity()).get(OrderViewModel::class.java)

        observeViewModel()
        setupPlaceOrder()
    }

    private fun observeViewModel() {

        cartViewModel.cartItems.observe(viewLifecycleOwner) { items ->
            binding.rvSummaryItems.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = CartSummaryAdapter(items)
            }
            binding.tvTotal.text = "$ ${cartViewModel.getTotalBill(items)}"
        }

        checkoutViewModel.deliveryAddress.observe(viewLifecycleOwner) { address ->
            binding.tvAddressTitle.text = address.title
            binding.tvAddress.text      = address.address
        }

        checkoutViewModel.paymentMethod.observe(viewLifecycleOwner) { method ->
            binding.tvPayment.text = method
        }

        orderViewModel.orderSuccess.observe(viewLifecycleOwner) { response ->
            response ?: return@observe

            orderViewModel.resetOrderSuccess()

            val action = CheckoutFragmentDirections
                .actionCheckoutToConfirmed(
                    orderId = response.order_id.toString()
                )
            parentFragment?.findNavController()?.navigate(action)
        }

        orderViewModel.error.observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }

        orderViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.btnPlaceOrder.isEnabled = !isLoading
            binding.btnPlaceOrder.text = if (isLoading) "Placing Order..." else "CONFIRM & PLACE ORDER"
        }
    }

    private fun setupPlaceOrder() {
        binding.btnPlaceOrder.setOnClickListener {
            val prefs     = requireActivity().getSharedPreferences("ShopEasySession", android.content.Context.MODE_PRIVATE)
            val userId    = prefs.getString("user_id", "") ?: ""
            val address   = checkoutViewModel.deliveryAddress.value
            val payment   = checkoutViewModel.paymentMethod.value
            val cartItems = cartViewModel.cartItems.value ?: emptyList()
            val total     = cartViewModel.getTotalBill(cartItems)

            when {
                address == null -> Toast.makeText(requireContext(), "No delivery address selected", Toast.LENGTH_SHORT).show()
                payment == null -> Toast.makeText(requireContext(), "No payment method selected", Toast.LENGTH_SHORT).show()
                cartItems.isEmpty() -> Toast.makeText(requireContext(), "Cart is empty", Toast.LENGTH_SHORT).show()
                else -> orderViewModel.placeOrder(userId, address, cartItems, total, payment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}