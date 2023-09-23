package com.example.haven.ui.signin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.haven.R
import com.example.haven.common.viewBinding
import com.example.haven.databinding.FragmentSignInBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private val binding by viewBinding(FragmentSignInBinding::bind)

    private val viewModel by viewModels<SignInViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            btnSignIn.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()

                if (!viewModel.isEmailValid(email)) {
                    showSnackbar(getString(R.string.error_invalid_email))
                    return@setOnClickListener
                }

                if (!viewModel.isPasswordValid(password)) {
                    showSnackbar(getString(R.string.error_invalid_password_length))
                    return@setOnClickListener
                }
                viewModel.signIn(email, password)
                observeSignIn()

            }

            tvDontHaveAnAccount.setOnClickListener {
                findNavController().navigate(R.id.signInToSignUp)
            }
        }

    }

    private fun observeSignIn() {
        viewModel.signInState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SignInState.Error -> {

                    Snackbar.make(requireView(), state.throwable.message.orEmpty(), 1000).show()
                }

                SignInState.Success -> {
                    findNavController().navigate(R.id.signInToHome)
                }
            }

        }
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
    }
}