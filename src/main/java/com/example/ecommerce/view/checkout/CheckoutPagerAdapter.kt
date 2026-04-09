package com.example.ecommerce.view.checkout

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class CheckoutPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount() = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CartItemsFragment()
            1 -> DeliveryFragment()
            2 -> PaymentFragment()
            3 -> SummaryFragment()
            else -> CartItemsFragment()
        }
    }
}