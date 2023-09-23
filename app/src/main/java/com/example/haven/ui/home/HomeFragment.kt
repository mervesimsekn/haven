package com.example.haven.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.haven.R
import com.example.haven.common.viewBinding
import com.example.haven.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), ProductsAdapter.ProductListener,
    OnSaleAdapter.ProductListener {

    private val binding by viewBinding(FragmentHomeBinding::bind)

    private val onSaleAdapter by lazy { OnSaleAdapter(this) }
    private val productsAdapter by lazy { ProductsAdapter(this) }

    private val viewModel by viewModels<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            rvOnSale.adapter = onSaleAdapter
            rvProducts.adapter = productsAdapter

            fabScrollToTop.setOnClickListener {
                nestedScrollView.smoothScrollTo(0, 0)
            }
            // FAB'in ekranı aşağıya kaydırdığımızda görüntülenmesi için
            nestedScrollView.setOnScrollChangeListener { _, _, scrollY, _, _ ->
                if (scrollY > 0) {
                    fabScrollToTop.visibility = View.VISIBLE
                } else {
                    fabScrollToTop.visibility = View.GONE
                }
            }
        }
        viewModel.getSaleProducts()
        viewModel.getProducts()

        observeData()
    }


    private fun observeData() {
        viewModel.homeState.observe(viewLifecycleOwner) { state ->
            when (state) {
                HomeState.Loading -> {
                    binding.pbProducts.visibility = View.VISIBLE
                    binding.pbOnSale.visibility = View.VISIBLE
                }

                is HomeState.Data -> {
                    binding.pbProducts.visibility = View.GONE
                    binding.pbOnSale.visibility = View.GONE
                    productsAdapter.submitList(state.products)
                }

                is HomeState.Error -> {
                    binding.pbProducts.visibility = View.GONE
                    binding.pbOnSale.visibility = View.GONE
                    Snackbar.make(requireView(), state.throwable.message.orEmpty(), 1000).show()
                }

                is HomeState.SaleData -> {
                    onSaleAdapter.submitList(state.products)
                }
            }
        }
    }


    override fun onProductClick(id: Int) {
        val action = HomeFragmentDirections.homeToDetail(id)
        findNavController().navigate(action)
    }
}