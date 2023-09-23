package com.example.haven.ui.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.haven.R
import com.example.haven.common.viewBinding
import com.example.haven.databinding.FragmentSignUpBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private val binding by viewBinding(FragmentSignUpBinding::bind)

    private val viewModel by viewModels<SignUpViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            btnSignUp.setOnClickListener {
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
                viewModel.signUp(email, password)
                observeSignUp()

            }

            tvAlreadyHaveAnAccount.setOnClickListener {
                findNavController().navigate(R.id.signUpToSignIn)
            }
        }
    }

    private fun observeSignUp() {
        viewModel.signUpState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SignUpState.Error -> {

                    Snackbar.make(requireView(), state.throwable.message.orEmpty(), 1000).show()
                }

                SignUpState.Success -> {
                    findNavController().navigate(R.id.signUpToHome)
                }
            }

        }
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
    }

}