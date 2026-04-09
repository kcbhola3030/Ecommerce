package com.example.ecommerce.view.checkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ecommerce.databinding.FragmentCheckoutBinding
import com.google.android.material.tabs.TabLayoutMediator

class CheckoutFragment : Fragment() {

    private var _binding: FragmentCheckoutBinding? = null
    private val binding get() = _binding!!

    private val tabTitles = listOf("Cart Items", "Delivery", "Payment", "Summary")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCheckoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewPager()
    }

    private fun setupViewPager() {
        val adapter = CheckoutPagerAdapter(
            childFragmentManager,
            lifecycle
        )

        binding.viewPager.adapter = adapter

        binding.viewPager.isUserInputEnabled = false

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()

        binding.tabLayout.touchables.forEach { it.isClickable = false }
    }

    fun moveToNextTab() {
        val current = binding.viewPager.currentItem
        if (current < tabTitles.size - 1) {
            binding.viewPager.currentItem = current + 1
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}