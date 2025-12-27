package com.example.myapplicationeasyaiorder.ui.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplicationeasyaiorder.data.AiRepository
import kotlinx.coroutines.launch
import java.util.UUID

data class ChatMessage(
    val id: String = UUID.randomUUID().toString(),
    val text: String,
    val isUser: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)

class ChatViewModel(private val aiRepository: AiRepository) : ViewModel() {
    private val _messages = MutableLiveData<List<ChatMessage>>(emptyList())
    val messages: LiveData<List<ChatMessage>> = _messages
    
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    fun sendMessage(text: String) {
        if (text.isBlank()) return
        
        val currentList = _messages.value.orEmpty().toMutableList()
        currentList.add(ChatMessage(text = text, isUser = true))
        _messages.value = currentList
        
        _isLoading.value = true
        viewModelScope.launch {
            val response = aiRepository.chatWithAi(text)
            val updatedList = _messages.value.orEmpty().toMutableList()
            updatedList.add(ChatMessage(text = response, isUser = false))
            _messages.value = updatedList
            _isLoading.value = false
        }
    }
}
