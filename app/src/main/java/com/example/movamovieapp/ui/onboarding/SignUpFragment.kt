package com.example.movamovieapp.ui.onboarding

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.movamovieapp.R
import com.example.movamovieapp.databinding.FragmentSignUpBinding
import com.example.movamovieapp.utils.BaseFragment
import com.example.movamovieapp.utils.Validator
import com.example.movamovieapp.utils.makeGone
import com.example.movamovieapp.utils.makeVisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>(FragmentSignUpBinding::inflate) {

    private val signUpViewModel by viewModels<SignUpViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observData()
        binding.signupbutton.setOnClickListener{
            regUser()
        }

        binding.textViewSigin.setOnClickListener {
            findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToSignInFragment())
        }

        binding.materialToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }



    }

    private fun regUser(){
        val email = binding.emailtexxtfiel.text.toString().trim()
        val password = binding.passwordtextfield.text.toString().trim()
        if( Validator.isValidPassword(password) && Validator.isValidEmail(email)) {
            signUpViewModel.register(email,password)

        } else{
            Toast.makeText(context, "Fill the gaps correctly", Toast.LENGTH_SHORT).show()
        }

    }

    private fun observData(){
        signUpViewModel.authResult.observe(viewLifecycleOwner) {
            when (it) {
                is AuthUiState.Success -> {
                    binding.loadinganim.makeGone()
                    if(binding.checkBox.isChecked){
                        saveRememberMe()
                    }
                    findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToHomeFragment())

                }

                is AuthUiState.Error -> {
                    binding.loadinganim.makeGone()
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }

                is AuthUiState.Loading -> binding.loadinganim.makeVisible()
            }
        }
    }

    fun saveRememberMe(){
        val sp = requireActivity().getSharedPreferences("SharedPreference", Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putBoolean("RememberMe",true)
        editor.apply()
    }


}

