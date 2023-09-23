package com.example.haven.ui.search

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.haven.R
import com.example.haven.common.viewBinding
import com.example.haven.databinding.FragmentSearchBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search), SearchAdapter.ProductListener {

    private val binding by viewBinding(FragmentSearchBinding::bind)

    private val searchAdapter by lazy { SearchAdapter(this) }

    private val viewModel by viewModels<SearchViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            rvSearch.adapter = searchAdapter

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if ((newText?.length ?: 0) >= 3) {
                        if (newText != null) {
                            viewModel.getSearchResults(newText)
                        }
                    }
                    return true
                }
            })
        }

        observeData()
    }

    private fun observeData() {
        viewModel.searchState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SearchState.Data -> {
                    searchAdapter.submitList(state.products)
                }

                is SearchState.Error -> {
                    Snackbar.make(requireView(), state.throwable.message.orEmpty(), 1000).show()
                }
            }
        }

    }

    override fun onProductClick(id: Int) {
        val action = SearchFragmentDirections.searchToDetail(id)
        findNavController().navigate(action)
    }
}