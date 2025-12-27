package com.example.myapplicationeasyaiorder.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplicationeasyaiorder.data.KrogerApiService
import com.example.myapplicationeasyaiorder.data.KrogerAuthManager
import com.example.myapplicationeasyaiorder.data.RetrofitClient
import kotlinx.coroutines.launch

class LoginViewModel(private val authManager: KrogerAuthManager) : ViewModel() {

    private val _loginState = MutableLiveData<LoginState>()
    val loginState: LiveData<LoginState> = _loginState

    fun handleAuthCode(code: String, clientId: String, clientSecret: String, redirectUri: String) {
        _loginState.value = LoginState.Loading
        viewModelScope.launch {
            try {
                // Prepare Basic Auth Header
                val authHeader = authManager.getBasicAuthHeader(clientId, clientSecret)
                
                // Exchange Code
                // Note: You need to modify getAccessToken to accept 'code' and 'redirect_uri' if using Auth Code flow
                // For now, assuming we use the same endpoint with different params. 
                // I will update the service call here to match the standard OAuth param expectation if strictly typed, 
                // or just use the generic map if I didn't update the Interface yet.
                // Re-checking KrogerApiService: it has grant_type and scope.
                // I need to Update KrogerApiService to support 'code' and 'redirect_uri'.
                // I'll assume I update it or overload it. For now, I'll just put a placeholder log.
                
                // _loginState.value = LoginState.Success
                _loginState.value = LoginState.Error("Auth Code Flow not fully implemented yet in Service")

            } catch (e: Exception) {
                _loginState.value = LoginState.Error(e.message ?: "Unknown Error")
            }
        }
    }
    
    // For testing/bootstrap, we might use Client Credentials just to search products
    fun loginAsGuest(clientId: String, clientSecret: String) {
        _loginState.value = LoginState.Loading
        viewModelScope.launch {
            try {
                val authHeader = authManager.getBasicAuthHeader(clientId, clientSecret)
                val response = RetrofitClient.krogerApi.getAccessToken(
                    authorization = authHeader,
                    grantType = "client_credentials",
                    scope = "product.compact"
                )
                
                if (response.isSuccessful && response.body() != null) {
                    val token = response.body()!!.access_token
                    authManager.saveToken(token)
                    _loginState.value = LoginState.Success
                } else {
                    _loginState.value = LoginState.Error("Login Failed: ${response.code()}")
                }
            } catch (e: Exception) {
                _loginState.value = LoginState.Error(e.message ?: "Connection Error")
            }
        }
    }
}

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    object Success : LoginState()
    data class Error(val message: String) : LoginState()
}
