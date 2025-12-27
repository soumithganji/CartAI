package com.example.myapplicationeasyaiorder.data

import com.example.myapplicationeasyaiorder.model.NimChatRequest
import com.example.myapplicationeasyaiorder.model.NimChatResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface NvidiaNimService {
    @POST("chat/completions")
    suspend fun chatCompletion(
        @Header("Authorization") apiKey: String,
        @Body request: NimChatRequest
    ): Response<NimChatResponse>
}
