package com.example.myapplicationeasyaiorder.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplicationeasyaiorder.data.CartRepository
import com.example.myapplicationeasyaiorder.model.Cart
import com.example.myapplicationeasyaiorder.model.Resource
import kotlinx.coroutines.launch

class CartViewModel(private val repository: CartRepository) : ViewModel() {
    private val _cartState = MutableLiveData<Resource<Cart>>()
    val cartState: LiveData<Resource<Cart>> = _cartState

    fun loadCart() {
        _cartState.value = Resource.Loading
        viewModelScope.launch {
            when (val result = repository.getCart()) {
                is Resource.Success -> {
                    _cartState.value = Resource.Success(result.data.data)
                }
                is Resource.Error -> {
                    // Start with empty cart if not found or error for now?
                    // Better to show error.
                    _cartState.value = Resource.Error(result.message)
                }
                is Resource.Loading -> {
                    _cartState.value = Resource.Loading
                }
            }
        }
    }
}
