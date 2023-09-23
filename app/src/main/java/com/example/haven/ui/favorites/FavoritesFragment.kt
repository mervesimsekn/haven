package com.example.haven.ui.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.haven.R
import com.example.haven.common.viewBinding
import com.example.haven.databinding.FragmentFavoritesBinding

class FavoritesFragment : Fragment(R.layout.fragment_favorites), FavoritesAdapter.ProductListener {

    private val binding by viewBinding(FragmentFavoritesBinding::bind)

    private val favoritesAdapter by lazy { FavoritesAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            rvFavorites.adapter = favoritesAdapter
        }
    }

    override fun onProductClick(id: Int) {
        val action = FavoritesFragmentDirections.favoritesToDetail(id)
        findNavController().navigate(action)
    }

}