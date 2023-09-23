package com.example.haven.ui.success

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.haven.R
import com.example.haven.common.viewBinding
import com.example.haven.databinding.FragmentSuccessBinding

class SuccessFragment : Fragment(R.layout.fragment_success) {

    private val binding by viewBinding(FragmentSuccessBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            btnBackToHome.setOnClickListener {
                val action = SuccessFragmentDirections.successToHome()
                findNavController().navigate(action)
            }
        }
    }
}