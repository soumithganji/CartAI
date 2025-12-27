package com.example.myapplicationeasyaiorder.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.myapplicationeasyaiorder.data.LocalCartRepository.LocalCartItem
import com.example.myapplicationeasyaiorder.databinding.ItemLocalCartBinding

class LocalCartAdapter : ListAdapter<LocalCartItem, LocalCartAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLocalCartBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemLocalCartBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: LocalCartItem) {
            binding.itemName.text = item.name
            binding.itemPrice.text = "$${String.format("%.2f", item.price)}"
            binding.itemQuantity.text = "Qty: ${item.quantity}"

            if (!item.imageUrl.isNullOrEmpty()) {
                binding.itemImage.load(item.imageUrl) {
                    crossfade(true)
                    placeholder(android.R.drawable.ic_menu_gallery)
                    error(android.R.drawable.ic_menu_report_image)
                }
            } else {
                binding.itemImage.load(android.R.drawable.ic_menu_gallery)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<LocalCartItem>() {
        override fun areItemsTheSame(oldItem: LocalCartItem, newItem: LocalCartItem): Boolean {
            return oldItem.productId == newItem.productId
        }

        override fun areContentsTheSame(oldItem: LocalCartItem, newItem: LocalCartItem): Boolean {
            return oldItem == newItem
        }
    }
}
