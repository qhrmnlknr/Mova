package com.example.movamovieapp.ui.onboarding

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movamovieapp.api.NetworkResponse
import com.example.movamovieapp.api.Repostory
import com.example.movamovieapp.datamodels.Genre
import com.example.movamovieapp.datamodels.GenreResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val Repository: Repostory,
) : ViewModel() {

    private val _authResult = MutableLiveData<AuthUiState>()
    val authResult: LiveData<AuthUiState> get() = _authResult


    fun signIn(email: String, password : String){
        viewModelScope.launch {
            Repository.loginUser(email = email, password = password).collectLatest {
                when (it) {
                    is NetworkResponse.Success -> {
                        _authResult.postValue(AuthUiState.Success)
                    }

                    is NetworkResponse.Error -> {
                        _authResult.postValue(AuthUiState.Error(it.message.toString()))
                    }

                    is NetworkResponse.Loading -> {
                        _authResult.postValue(AuthUiState.Loading)
                    }
                }
            }
        }
    }

}

