package com.example.ecommerce.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce.databinding.ItemSpecificationBinding
import com.example.ecommerce.model.data.Specification

class SpecificationAdapter(
    private val specifications: List<Specification>
) : RecyclerView.Adapter<SpecificationAdapter.SpecViewHolder>() {

    inner class SpecViewHolder(val binding: ItemSpecificationBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(spec: Specification) {
            binding.tvTitle.text = spec.title
            binding.tvValue.text = spec.specification
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecViewHolder {
        val binding = ItemSpecificationBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SpecViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SpecViewHolder, position: Int) {
        holder.bind(specifications[position])
    }

    override fun getItemCount() = specifications.size
}