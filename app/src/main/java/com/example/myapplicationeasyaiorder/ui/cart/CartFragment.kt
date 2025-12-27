package com.example.myapplicationeasyaiorder.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplicationeasyaiorder.databinding.FragmentCartBinding
import com.example.myapplicationeasyaiorder.model.Resource
import com.example.myapplicationeasyaiorder.ui.EasyOrderViewModelFactory

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CartViewModel by viewModels {
        EasyOrderViewModelFactory(requireContext())
    }

    private val cartAdapter = CartAdapter { item, newQty ->
        viewModel.updateQuantity(item, newQty)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cartRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = cartAdapter
        }

        viewModel.cartState.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.loadingBar.visibility = View.VISIBLE
                    binding.emptyText.visibility = View.GONE
                }
                is Resource.Success -> {
                    binding.loadingBar.visibility = View.GONE
                    if (resource.data.items.isEmpty()) {
                        binding.emptyText.visibility = View.VISIBLE
                    } else {
                        binding.emptyText.visibility = View.GONE
                        cartAdapter.submitList(resource.data.items)
                    }
                }
                is Resource.Error -> {
                    binding.loadingBar.visibility = View.GONE
                    Toast.makeText(context, resource.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.loadCart()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
