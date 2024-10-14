package com.example.movamovieapp.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.movamovieapp.databinding.FragmentSplashBinding
import com.example.movamovieapp.utils.BaseFragment
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CoroutineScope(Dispatchers.Main).launch{
            delay(2500)

            checkLogin()


        }




    }

    fun checkLogin(){
        val sp = requireActivity().getSharedPreferences("SharedPreference", Context.MODE_PRIVATE)
        val isLoggedIn = sp.getBoolean("RememberMe",false)

        when(isLoggedIn){
            true-> findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
            false->findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToWelcomeFragment())
        }

    }



}