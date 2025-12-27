package com.example.myapplicationeasyaiorder.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplicationeasyaiorder.databinding.FragmentLoginBinding
import com.example.myapplicationeasyaiorder.data.KrogerAuthManager
import com.example.myapplicationeasyaiorder.ui.EasyOrderViewModelFactory
import androidx.navigation.fragment.findNavController

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels {
        EasyOrderViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginButton.setOnClickListener {
            val clientId = binding.clientIdInput.editText?.text.toString()
            val secret = binding.clientSecretInput.editText?.text.toString()
            
            if (clientId.isNotBlank() && secret.isNotBlank()) {
                // Using Guest/Client Credentials flow for initial setup
                viewModel.loginAsGuest(clientId, secret)
            } else {
                Toast.makeText(context, "Please enter API Keys", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.loginState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is LoginState.Loading -> {
                    binding.loadingBar.visibility = View.VISIBLE
                    binding.loginButton.isEnabled = false
                }
                is LoginState.Success -> {
                    binding.loadingBar.visibility = View.GONE
                    binding.loginButton.isEnabled = true
                    Toast.makeText(context, "Connected Successfully!", Toast.LENGTH_SHORT).show()
                    // Navigate to Cart
                    findNavController().navigate(com.example.myapplicationeasyaiorder.R.id.cartFragment)
                }
                is LoginState.Error -> {
                    binding.loadingBar.visibility = View.GONE
                    binding.loginButton.isEnabled = true
                    Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
                }
                else -> {}
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
