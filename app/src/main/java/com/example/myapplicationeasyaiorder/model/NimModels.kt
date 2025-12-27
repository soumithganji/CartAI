package com.example.myapplicationeasyaiorder.model

data class NimChatRequest(
    val model: String,
    val messages: List<NimMessage>,
    val max_tokens: Int = 1024,
    val temperature: Double = 0.2,
    val top_p: Double = 0.7,
    val stream: Boolean = false
)

data class NimMessage(
    val role: String,
    val content: String
)

data class NimChatResponse(
    val id: String,
    val choices: List<NimChoice>
)

data class NimChoice(
    val index: Int,
    val message: NimMessage
)
