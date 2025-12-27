package com.example.myapplicationeasyaiorder.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplicationeasyaiorder.data.KrogerAuthManager
import com.example.myapplicationeasyaiorder.ui.login.LoginViewModel
import com.example.myapplicationeasyaiorder.ui.cart.CartViewModel

class EasyOrderViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            val authManager = KrogerAuthManager(context)
            return LoginViewModel(authManager) as T
        }
        if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            val authManager = KrogerAuthManager(context)
            return CartViewModel(com.example.myapplicationeasyaiorder.data.CartRepository(authManager)) as T
        }
        if (modelClass.isAssignableFrom(com.example.myapplicationeasyaiorder.ui.chat.ChatViewModel::class.java)) {
            // TODO: Retrieve API Key from Secrets/Preferences
            return com.example.myapplicationeasyaiorder.ui.chat.ChatViewModel(
                com.example.myapplicationeasyaiorder.data.AiRepositoryImpl("TODO_GEMINI_API_KEY")
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
