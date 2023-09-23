package com.example.haven.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.haven.R
import com.example.haven.common.viewBinding
import com.example.haven.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val binding by viewBinding(FragmentProfileBinding::bind)

    private val auth = FirebaseAuth.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            btnSignOut.setOnClickListener {
                auth.signOut()
                val action = ProfileFragmentDirections.profileToSignIn()
                findNavController().navigate(action)
            }
        }
    }

}