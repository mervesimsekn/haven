package com.example.haven.ui.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.haven.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val viewModel: SplashViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_out)

        showSplashScreenAndCheckAuthState(animation)

    }

    private fun observeLoginStatusAndNavigate(animation: Animation) {
        viewModel.isUserLoggedIn.observe(viewLifecycleOwner) { isUserLoggedIn ->
            if (isUserLoggedIn) {
                findNavController().navigate(R.id.homeFragment)
            } else {
                findNavController().navigate(R.id.signInFragment)
            }
        }
    }

    private fun showSplashScreenAndCheckAuthState(animation: Animation) {
        lifecycleScope.launch {
            delay(3000)
            observeLoginStatusAndNavigate(animation)
        }
    }
}