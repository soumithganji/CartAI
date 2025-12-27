package com.example.myapplicationeasyaiorder.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationeasyaiorder.databinding.ItemCartBinding
import com.example.myapplicationeasyaiorder.model.CartItem

class CartAdapter : ListAdapter<CartItem, CartAdapter.CartViewHolder>(CartDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CartViewHolder(private val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CartItem) {
            binding.itemName.text = "Item ${item.itemId}" // Make description later if populated
            binding.itemPrice.text = "$%.2f".format(item.price)
            binding.itemQuantity.text = "Qty: ${item.quantity}"
        }
    }

    class CartDiffCallback : DiffUtil.ItemCallback<CartItem>() {
        override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem) = oldItem.itemId == newItem.itemId
        override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem) = oldItem == newItem
    }
}
