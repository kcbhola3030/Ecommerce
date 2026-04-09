package com.example.ecommerce.view.checkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.ecommerce.databinding.FragmentPaymentBinding
import com.example.ecommerce.viewmodel.CheckoutViewModel

class PaymentFragment : Fragment() {

    private var _binding: FragmentPaymentBinding? = null
    private val binding get() = _binding!!
    private lateinit var checkoutViewModel: CheckoutViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkoutViewModel = ViewModelProvider(requireActivity()).get(CheckoutViewModel::class.java)

        binding.btnNext.setOnClickListener {
            val selectedMethod = getSelectedPaymentMethod()
            if (selectedMethod == null) {
                Toast.makeText(requireContext(), "Please select a payment method", Toast.LENGTH_SHORT).show()
            } else {
                checkoutViewModel.setPaymentMethod(selectedMethod)
                (parentFragment as CheckoutFragment).moveToNextTab()
            }
        }
    }

    private fun getSelectedPaymentMethod(): String? {
        return when (binding.radioGroupPayment.checkedRadioButtonId) {
            binding.rbCod.id      -> "COD"
            binding.rbInternet.id -> "Internet Banking"
            binding.rbCard.id     -> "Debit/Credit Card"
            binding.rbPaypal.id   -> "PayPal"
            else                  -> null
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}