package com.example.movamovieapp.ui.mylist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movamovieapp.api.Repostory
import com.example.movamovieapp.datamodels.local.FavoriteMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyListViewModel @Inject constructor(private val repostory: Repostory) : ViewModel() {

    var favorites = MutableLiveData<List<FavoriteMovies>>(listOf())

    init {
        getFavorites()
    }

    fun getFavorites(){
        viewModelScope.launch {
            favorites.value = repostory.getFavorites()
        }

    }

    fun removeMovieItem(item : FavoriteMovies){
        viewModelScope.launch {
            repostory.removeItem(item)
            getFavorites()
        }
    }







}