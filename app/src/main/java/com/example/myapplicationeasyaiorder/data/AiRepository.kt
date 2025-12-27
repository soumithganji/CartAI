package com.example.myapplicationeasyaiorder.data

import android.graphics.Bitmap

interface AiRepository {
    suspend fun chatWithAi(prompt: String): String
    suspend fun analyzeImageForItems(image: Bitmap): List<String>
}
