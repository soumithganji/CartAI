package com.example.myapplicationeasyaiorder.data

import com.example.myapplicationeasyaiorder.model.Product
import com.example.myapplicationeasyaiorder.model.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductRepository(private val authManager: KrogerAuthManager) {

    suspend fun searchProducts(term: String, locationId: String = "01400943"): Resource<List<Product>> {
        // Default locationId for now: 01400943 (Example Store)
        val token = authManager.getToken()
        if (token.isNullOrEmpty()) return Resource.Error("User not logged in")

        return withContext(Dispatchers.IO) {
            try {
                val response = RetrofitClient.krogerApi.searchProducts("Bearer $token", term, locationId)
                if (response.isSuccessful && response.body() != null) {
                    Resource.Success(response.body()!!.data)
                } else {
                    Resource.Error("Error searching products: ${response.code()} ${response.message()}")
                }
            } catch (e: Exception) {
                Resource.Error(e.message ?: "Network Error")
            }
        }
    }

    suspend fun findCheapestVariant(term: String): Resource<Product> {
        return when (val result = searchProducts(term)) {
            is Resource.Success -> {
                val products = result.data
                // Logic: Filter products with price, sort by price (ascending)
                val cheapest = products.minByOrNull { 
                    it.items.firstOrNull()?.price?.regular ?: Double.MAX_VALUE 
                }
                
                if (cheapest != null) Resource.Success(cheapest)
                else Resource.Error("No products found for $term")
            }
            is Resource.Error -> Resource.Error(result.message)
            else -> Resource.Error("Unknown error")
        }
    }
}
