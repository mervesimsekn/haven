package com.example.haven.ui.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.haven.R
import com.example.haven.common.viewBinding
import com.example.haven.data.model.User
import com.example.haven.databinding.FragmentCartBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment(R.layout.fragment_cart), CartAdapter.ProductListener {

    private val binding by viewBinding(FragmentCartBinding::bind)

    private val cartAdapter by lazy { CartAdapter(this, viewModel) }

    private lateinit var auth: FirebaseAuth

    private val viewModel by viewModels<CartViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        val userId = auth.currentUser!!.uid

        viewModel.getCartProducts(userId)

        observeData()
    }

    private fun observeData() = with(binding) {
        viewModel.cartState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is CartState.Data -> {
                    rvCart.adapter = cartAdapter
                    cartAdapter.submitList(state.products)
                    if (state.products.isEmpty().not()) {
                        tvEmptyCart.visibility = View.GONE
                        tvClearCart.visibility = View.VISIBLE
                        btnConfirmCart.visibility = View.VISIBLE
                    } else {
                        tvEmptyCart.visibility = View.VISIBLE
                        tvClearCart.visibility = View.GONE
                        btnConfirmCart.visibility = View.GONE
                    }
                    val userId = auth.currentUser?.uid
                    tvClearCart.setOnClickListener {
                        val user = User(userId)
                        println(user)
                        viewModel.clearCart(user)
                    }

                    btnConfirmCart.setOnClickListener {
                        findNavController().navigate(R.id.paymentFragment)
                    }
                }

                is CartState.ClearCart -> {
                    cartAdapter.submitList(emptyList())
                    tvEmptyCart.visibility = View.VISIBLE
                    tvClearCart.visibility = View.GONE
                    btnConfirmCart.visibility = View.GONE
                    println(state.message)
                }

                is CartState.DeleteProduct -> {
                    println(state.message)
                    val currentCart = cartAdapter.currentList
                    val updatedCart =
                        currentCart.filter { it?.id != state.deleteFromCartItem.id }
                    cartAdapter.submitList(updatedCart)
                    if (updatedCart.isEmpty()) {
                        tvEmptyCart.visibility = View.VISIBLE
                        tvClearCart.visibility = View.GONE
                        btnConfirmCart.visibility = View.GONE
                    } else {
                        tvEmptyCart.visibility = View.GONE
                        tvClearCart.visibility = View.VISIBLE
                        btnConfirmCart.visibility = View.VISIBLE
                    }
                }

                is CartState.Error -> {
                    Snackbar.make(requireView(), state.throwable.message.orEmpty(), 1000).show()
                }

            }
        }
    }

    override fun onProductClick(id: Int) {
        val action = CartFragmentDirections.cartToProductDetail(id)
        findNavController().navigate(action)
    }
}