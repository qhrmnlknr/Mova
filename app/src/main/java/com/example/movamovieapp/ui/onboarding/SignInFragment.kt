package com.example.movamovieapp.ui.onboarding

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.movamovieapp.databinding.FragmentSignInBinding
import com.example.movamovieapp.utils.BaseFragment
import com.example.movamovieapp.utils.Validator
import com.example.movamovieapp.utils.makeGone
import com.example.movamovieapp.utils.makeVisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding>(FragmentSignInBinding::inflate) {

    private val signInViewModel by viewModels<SignInViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        binding.signinbutton.setOnClickListener {
            signInUser()
        }

        binding.textViewSigin.setOnClickListener {
            findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToSignUpFragment())
        }

        binding.materialToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }


    }

    private fun signInUser(){
        val email = binding.emailtextfield.text.toString().trim()
        val password = binding.passwordtextfield.text.toString().trim()
        if( Validator.isValidPassword(password) && Validator.isValidEmail(email)) {
            signInViewModel.signIn(email,password)
        } else{
            Toast.makeText(context, "Fill the gaps correctly", Toast.LENGTH_SHORT).show()
        }

    }


    private fun observeData() {
       signInViewModel.authResult.observe(viewLifecycleOwner){
           when (it) {
               is AuthUiState.Success -> {
                   binding.loadinganim.makeGone()
                   findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToHomeFragment())
               }

               is AuthUiState.Error -> {
                   binding.loadinganim.makeGone()
                   Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
               }

               is AuthUiState.Loading -> binding.loadinganim.makeVisible()
           }
       }
    }

}