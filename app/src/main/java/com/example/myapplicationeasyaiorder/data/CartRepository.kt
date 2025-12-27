package com.example.myapplicationeasyaiorder.data

import com.example.myapplicationeasyaiorder.model.CartResponse
import com.example.myapplicationeasyaiorder.model.Resource
import com.example.myapplicationeasyaiorder.model.CartUpdateRequest

class CartRepository(private val authManager: KrogerAuthManager) {

    suspend fun getCart(): Resource<CartResponse> {
        val token = authManager.getToken()
        if (token.isNullOrEmpty()) return Resource.Error("User not logged in")

        return try {
            val response = RetrofitClient.krogerApi.getCart("Bearer $token")
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error("Error: ${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Network Error")
        }
    }
}
