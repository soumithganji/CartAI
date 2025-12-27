package com.example.myapplicationeasyaiorder.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplicationeasyaiorder.databinding.FragmentChatBinding
import com.example.myapplicationeasyaiorder.ui.EasyOrderViewModelFactory

class ChatFragment : Fragment() {

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ChatViewModel by viewModels {
        EasyOrderViewModelFactory(requireContext())
    }

    private val chatAdapter = ChatAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.chatRecyclerView.apply {
            layoutManager = LinearLayoutManager(context).apply {
                stackFromEnd = true // Start from bottom
            }
            adapter = chatAdapter
        }

        binding.sendButton.setOnClickListener {
            val text = binding.messageInput.text.toString()
            if (text.isNotBlank()) {
                viewModel.sendMessage(text)
                binding.messageInput.text.clear()
            }
        }

        viewModel.messages.observe(viewLifecycleOwner) { messages ->
            chatAdapter.submitList(messages) {
                if (messages.isNotEmpty()) {
                    binding.chatRecyclerView.scrollToPosition(messages.size - 1)
                }
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.loadingBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
