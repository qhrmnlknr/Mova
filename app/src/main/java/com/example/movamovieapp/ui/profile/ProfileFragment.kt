package com.example.movamovieapp.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.movamovieapp.databinding.FragmentProfileBinding
import com.example.movamovieapp.utils.BaseFragment

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val viewmodel by viewModels<ProfileViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button8.setOnClickListener {
            logOut()
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragment2ToSplashFragment())
        }

    }

    fun logOut(){
        val sp = requireActivity().getSharedPreferences("SharedPreference", Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putBoolean("RememberMe",false)
        editor.apply()
    }

}