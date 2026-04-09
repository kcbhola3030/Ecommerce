package com.example.ecommerce.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerce.R
import com.example.ecommerce.databinding.FragmentOrderConfirmedBinding
import com.example.ecommerce.view.checkout.CartSummaryAdapter
import com.example.ecommerce.viewmodel.CartViewModel
import com.example.ecommerce.viewmodel.CheckoutViewModel

class OrderConfirmedFragment : Fragment() {

    private var _binding: FragmentOrderConfirmedBinding? = null
    private val binding get() = _binding!!
    private val args: OrderConfirmedFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderConfirmedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cartViewModel     = ViewModelProvider(requireActivity()).get(CartViewModel::class.java)
        val checkoutViewModel = ViewModelProvider(requireActivity()).get(CheckoutViewModel::class.java)

        binding.tvOrderId.text = "#${args.orderId}"

        cartViewModel.cartItems.observe(viewLifecycleOwner) { items ->
            binding.rvOrderItems.apply {
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

        binding.btnCancelOrder.setOnClickListener {
            cartViewModel.clearCart()
            findNavController().navigate(R.id.homeFragment)
        }

        checkoutViewModel.paymentMethod.observe(viewLifecycleOwner) { method ->
            binding.tvPayment.text = method
        }



    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}