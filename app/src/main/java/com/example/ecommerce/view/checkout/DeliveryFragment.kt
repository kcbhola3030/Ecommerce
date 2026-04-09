package com.example.ecommerce.view.checkout

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecommerce.databinding.FragmentDeliveryBinding
import com.example.ecommerce.model.data.Address
import com.example.ecommerce.viewmodel.AddressViewModel
import com.example.ecommerce.viewmodel.AuthViewModel
import com.example.ecommerce.viewmodel.CheckoutViewModel

class DeliveryFragment : Fragment() {

    private var _binding: FragmentDeliveryBinding? = null
    private val binding get() = _binding!!
    private lateinit var addressViewModel: AddressViewModel
    private lateinit var checkoutViewModel: CheckoutViewModel
    private var selectedAddress: Address? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDeliveryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addressViewModel = ViewModelProvider(requireActivity()).get(AddressViewModel::class.java)
        checkoutViewModel = ViewModelProvider(requireActivity()).get(CheckoutViewModel::class.java)

        val prefs  = requireActivity().getSharedPreferences("ShopEasySession", android.content.Context.MODE_PRIVATE)
        val userId = prefs.getString("user_id", "") ?: ""
        addressViewModel.getAddresses(userId)

        observeViewModel()
        setupClickListeners(userId)
    }

    private fun observeViewModel() {
        addressViewModel.addresses.observe(viewLifecycleOwner) { addresses ->
            binding.rvAddresses.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = AddressAdapter(addresses) { address ->
                    selectedAddress = address
                    checkoutViewModel.setDeliveryAddress(address)
                }
            }
        }
    }

    private fun setupClickListeners(userId: String) {

        binding.btnAddAddress.setOnClickListener {
            showAddAddressDialog(userId)
        }

        binding.btnNext.setOnClickListener {
            if (selectedAddress == null) {
                Toast.makeText(requireContext(), "Please select an address", Toast.LENGTH_SHORT).show()
            } else {
                (parentFragment as CheckoutFragment).moveToNextTab()
            }
        }
    }

    private fun showAddAddressDialog(userId: String) {
        val binding = com.example.ecommerce.databinding.DialogAddAddressBinding
            .inflate(layoutInflater)

        AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .create()
            .also { dialog ->
                binding.btnCancel.setOnClickListener { dialog.dismiss() }
                binding.btnSave.setOnClickListener {
                    val title   = binding.etTitle.text.toString().trim()
                    val address = binding.etAddress.text.toString().trim()
                    if (title.isEmpty() || address.isEmpty()) {
                        Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
                    } else {
                        addressViewModel.addAddress(userId, title, address)
                        dialog.dismiss()
                    }
                }
                dialog.show()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}