package com.example.haven.ui.detail

import android.graphics.Paint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.haven.R
import com.example.haven.common.viewBinding
import com.example.haven.data.model.CartItem
import com.example.haven.databinding.FragmentProductDetailBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailFragment : Fragment(R.layout.fragment_product_detail) {

    private val binding by viewBinding(FragmentProductDetailBinding::bind)

    private val args by navArgs<ProductDetailFragmentArgs>()

    private lateinit var auth: FirebaseAuth

    private val viewModel by viewModels<ProductDetailViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        auth.currentUser?.uid

        viewModel.getProductDetail(args.id)

        observeData()
    }

    private fun observeData() = with(binding) {
        viewModel.detailState.observe(viewLifecycleOwner) { state ->
            when (state) {
                DetailState.Loading -> {
                    progressBar.visibility = View.VISIBLE
                }

                is DetailState.AddProduct -> {
                    progressBar.visibility = View.GONE
                    println(state.message)

                    when (state.message) {
                        "The product successfully added to cart" -> {
                            showSnackbar(getString(R.string.alert_added_to_cart))
                        }

                        "The product is already in the cart" -> {
                            showSnackbar(getString(R.string.alert_already_added_to_cart))
                        }
                    }

                }

                is DetailState.Data -> {
                    progressBar.visibility = View.GONE

                    ivBackButton.setOnClickListener {
                        findNavController().navigateUp()
                    }

                    val imageUrls = arrayListOf<String?>()
                    imageUrls.add(state.product.imageOne)
                    imageUrls.add(state.product.imageTwo)
                    val adapter = ImageSliderAdapter(imageUrls)
                    viewPager.adapter = adapter
                    //viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

                    tvDetailProductTitle.text = state.product.title.substringBefore("-")
                    tvDetailProductAuthor.text = state.product.title.substringAfter("-")

                    ratingBar.rating = state.product.rate.toFloat()

                    tvProductDetailDescription.text = state.product.description

                    // Ürünlerin İndirimde Olması Durumunda Normal Fiyat Üstü Çizilli Şekilde Görüntülenirken
                    // İndirimli Fiyat Dikkat Çekmesi Adına Kırmızı İle Görüntüleniyor.
                    if (state.product.saleState) {
                        tvPrice.text = state.product.price.toString()
                        tvPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                        tvDiscountedPrice.text = "${state.product.salePrice} TL"
                        tvDiscountedPrice.visibility = View.VISIBLE
                        tvDiscountedPrice.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.warning_color
                            )
                        )
                    } else {
                        tvPrice.text = "${state.product.price} TL"
                        tvPrice.paintFlags = 0
                        tvDiscountedPrice.visibility = View.GONE
                    }

                    // Ürünlerin Stoğunun 20'den Az Olması Durumunda Tükeniyor Uyarısı
                    // Örnek: Leonardo ve Witchcraft Kitaplarının Stok Durumu 20'den Az
                    if (state.product.count < 20) {
                        tvCount.visibility = View.VISIBLE
                        tvCount.text = getString(R.string.warning_running_out)
                        tvCount.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.warning_color
                            )
                        )
                    } else {
                        tvCount.visibility = View.GONE
                    }

                    tvCategory.text = state.product.category
                    when (state.product.category) {
                        "Edebiyat" -> tvCategory.setBackgroundColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.literature_category
                            )
                        )

                        "Sanat" -> tvCategory.setBackgroundColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.art_category
                            )
                        )

                        "Çocuk" -> tvCategory.setBackgroundColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.children_category
                            )
                        )

                        "Tarih" -> tvCategory.setBackgroundColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.history_category
                            )
                        )

                        "Çizgi Roman" -> tvCategory.setBackgroundColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.comic_category
                            )
                        )
                    }

                    btnAddToCart.setOnClickListener {
                        val cartItem = CartItem(state.product.id, auth.currentUser?.uid)
                        viewModel.addToCart(cartItem)
                    }
                    /*
                    ivFavorite.setOnClickListener {
                        viewModel.addProductToFavorites(state.product)
                    }
                     */

                }

                is DetailState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Snackbar.make(requireView(), state.throwable.message.orEmpty(), 1000).show()
                }
            }
        }
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
    }
}