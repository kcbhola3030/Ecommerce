package com.example.ecommerce.view.checkout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce.databinding.ItemAddressBinding
import com.example.ecommerce.model.data.Address

class AddressAdapter(
    private val addresses: List<Address>,
    private val onAddressSelected: (Address) -> Unit
) : RecyclerView.Adapter<AddressAdapter.AddressViewHolder>() {

    private var selectedPosition = -1

    inner class AddressViewHolder(val binding: ItemAddressBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(address: Address, position: Int) {
            binding.tvTitle.text   = address.title
            binding.tvAddress.text = address.address

            binding.rbSelect.isChecked = (position == selectedPosition)

            binding.root.setOnClickListener {
                val previousPosition = selectedPosition
                selectedPosition = position

                notifyItemChanged(previousPosition)
                notifyItemChanged(selectedPosition)

                onAddressSelected(address)
            }

            binding.rbSelect.setOnClickListener {
                val previousPosition = selectedPosition
                selectedPosition = position
                notifyItemChanged(previousPosition)
                notifyItemChanged(selectedPosition)
                onAddressSelected(address)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        val binding = ItemAddressBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return AddressViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        holder.bind(addresses[position], position)
    }

    override fun getItemCount() = addresses.size
}