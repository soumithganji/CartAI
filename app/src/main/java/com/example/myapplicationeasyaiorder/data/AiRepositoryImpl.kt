package com.example.myapplicationeasyaiorder.data

import android.graphics.Bitmap
import com.example.myapplicationeasyaiorder.model.NimChatRequest
import com.example.myapplicationeasyaiorder.model.NimMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class AiRepositoryImpl(private val apiKey: String) : AiRepository {

    private val nimService: NvidiaNimService by lazy {
        val logging = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

        Retrofit.Builder()
            .baseUrl("https://integrate.api.nvidia.com/v1/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NvidiaNimService::class.java)
    }

    override suspend fun chatWithAi(prompt: String): String {
        return withContext(Dispatchers.IO) {
            try {
                val request = NimChatRequest(
                    model = "meta/llama-3.1-405b-instruct",
                    messages = listOf(NimMessage("user", prompt))
                )
                val response = nimService.chatCompletion("Bearer $apiKey", request)
                if (response.isSuccessful) {
                    response.body()?.choices?.firstOrNull()?.message?.content ?: "No response from AI."
                } else {
                    "Error: ${response.code()} ${response.message()}"
                }
            } catch (e: Exception) {
                "Error processing request: ${e.localizedMessage}"
            }
        }
    }

    override suspend fun analyzeImageForItems(image: Bitmap): List<String> {
        return withContext(Dispatchers.IO) {
            // TODO: Implement Vision logic using a VLM from NIM (e.g. neva)
            // For now, return a placeholder as strictly requested to use NIM which might not have easy VLM standard endpoint yet in this simple setup
            // Or use "nvidia/neva-22b" if compatible with chat/completions (usually requires image_url content)
            
            // Stubbing for now to ensure compilation and safe switch
            listOf("Apple", "Banana", "Milk (Simulated from NIM Stub)")
        }
    }
}
