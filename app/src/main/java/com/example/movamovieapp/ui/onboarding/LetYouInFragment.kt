package com.example.movamovieapp.ui.onboarding

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.movamovieapp.databinding.FragmentLetYouInBinding
import com.example.movamovieapp.utils.BaseFragment

class LetYouInFragment : BaseFragment<FragmentLetYouInBinding>(FragmentLetYouInBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSigninPassword.setOnClickListener {
            findNavController().navigate(LetYouInFragmentDirections.actionLetYouInFragmentToSignInFragment())
        }

        binding.textViewSignupp.setOnClickListener {
            findNavController().navigate(LetYouInFragmentDirections.actionLetYouInFragmentToSignUpFragment())
        }

        binding.materialToolbar2.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

    }

}