package com.example.haven.ui.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.haven.R
import com.example.haven.common.viewBinding
import com.example.haven.databinding.FragmentPaymentBinding
import com.google.android.material.snackbar.Snackbar

class PaymentFragment : Fragment(R.layout.fragment_payment) {

    private val binding by viewBinding(FragmentPaymentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            ivBackToCart.setOnClickListener {
                findNavController().navigateUp()
            }

            btnCompletePayment.setOnClickListener {
                val addressTitle = etAddressTitle.text
                val city = etCity.text
                val district = etDistrict.text
                val cardOwner = etCardOwner.text
                val cardNumber = etCardNumber.text
                val month = etMonth.text
                val year = etYear.text
                val cvc = etCvc.text

                if (addressTitle.isNullOrEmpty() || city.isNullOrEmpty() || district.isNullOrEmpty() || cardOwner.isNullOrEmpty() || cardNumber.isNullOrEmpty() || cvc.isNullOrEmpty() || month.isNullOrEmpty() || year.isNullOrEmpty()) {
                    showSnackbar(getString(R.string.error_empty_field))
                } else if (cardNumber.length != 16) {
                    showSnackbar(getString(R.string.error_invalid_card_number_digit))
                } else if (cvc.length != 3) {
                    showSnackbar(getString(R.string.error_invalid_cvc_digit))
                } else if (year.length != 4) {
                    showSnackbar(getString(R.string.error_invalid_year_digit))
                } else if (month.length != 2) {
                    showSnackbar(getString(R.string.error_invalid_month_digit))
                } else {
                    findNavController().navigate(R.id.successFragment)
                }
            }
        }
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
    }
}
