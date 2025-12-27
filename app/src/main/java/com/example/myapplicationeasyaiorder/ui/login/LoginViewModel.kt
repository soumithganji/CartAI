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

    fun getAuthIntent(clientId: String): android.content.Intent {
        return authManager.getAuthIntent(clientId)
    }

    fun handleAuthResponse(response: net.openid.appauth.AuthorizationResponse, clientSecret: String) {
        _loginState.value = LoginState.Loading
        // Create Token Request from response
        val tokenRequest = response.createTokenExchangeRequest()
        
        authManager.performTokenRequest(tokenRequest, clientSecret) { token, error ->
            if (token != null) {
                _loginState.postValue(LoginState.Success)
            } else {
                 _loginState.postValue(LoginState.Error(error?.message ?: "Token exchange failed"))
            }
        }
    }
    
    fun resetLoginState() {
        _loginState.value = LoginState.Idle
    }
}

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    object Success : LoginState()
    data class Error(val message: String) : LoginState()
}
