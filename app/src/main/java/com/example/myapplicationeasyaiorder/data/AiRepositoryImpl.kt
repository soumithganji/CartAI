package com.example.myapplicationeasyaiorder.data

import android.graphics.Bitmap
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AiRepositoryImpl(private val apiKey: String) : AiRepository {

    // Using Gemini Pro for text
    private val textModel = GenerativeModel(
        modelName = "gemini-pro",
        apiKey = apiKey
    )

    // Using Gemini Pro Vision for images
    private val visionModel = GenerativeModel(
        modelName = "gemini-pro-vision",
        apiKey = apiKey
    )

    override suspend fun chatWithAi(prompt: String): String {
        return withContext(Dispatchers.IO) {
            try {
                val response = textModel.generateContent(prompt)
                response.text ?: "I'm sorry, I didn't understand that."
            } catch (e: Exception) {
                "Error processing request: ${e.localizedMessage}"
            }
        }
    }

    override suspend fun analyzeImageForItems(image: Bitmap): List<String> {
        return withContext(Dispatchers.IO) {
            try {
                val prompt = content {
                   image(image)
                   text("List ONLY the grocery items visible in this image as a simple comma separated list. Do not include quantity or other text.")
                }
                val response = visionModel.generateContent(prompt)
                val text = response.text ?: ""
                text.split(",").map { it.trim() }.filter { it.isNotEmpty() }
            } catch (e: Exception) {
                emptyList()
            }
        }
    }
}
