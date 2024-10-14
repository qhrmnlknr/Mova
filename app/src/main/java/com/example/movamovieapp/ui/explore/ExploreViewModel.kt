package com.example.movamovieapp.ui.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movamovieapp.api.NetworkResponse
import com.example.movamovieapp.api.Repostory
import com.example.movamovieapp.ui.home.GenresUiState
import com.example.movamovieapp.ui.home.HomeUiState
import com.example.movamovieapp.ui.home.MovieDetailUiState
import com.example.movamovieapp.ui.home.SearchMovieUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor (private val repostory: Repostory): ViewModel() {

    init {
        getGenres()

    }

    private val _genreUIState = MutableLiveData<GenresUiState>()
    val genreUIState: LiveData<GenresUiState> get() = _genreUIState

    private val _searchUIState = MutableLiveData<SearchMovieUiState>()
    val senreUIState: LiveData<SearchMovieUiState> get() = _searchUIState

    private val _movieUiStateData = MutableLiveData<HomeUiState>()
    val movieUiStateData: LiveData<HomeUiState> get() = _movieUiStateData




    fun searchMovie(keyword : String){
        viewModelScope.launch {
            repostory.searchMovies(keyword).collectLatest {
                when (it) {
                    is NetworkResponse.Loading -> _searchUIState.postValue(SearchMovieUiState.Loading)

                    is NetworkResponse.Success -> _searchUIState.postValue(it.let { it1 ->
                        SearchMovieUiState.SuccessNowPlaying(it1.data)
                    })

                    is NetworkResponse.Error -> _searchUIState.postValue(it.message.let { it1 ->
                        SearchMovieUiState.Error(
                            it1
                        )
                    })
                }
            }
        }
    }




    fun getGenres(){
        viewModelScope.launch {
            repostory.getGenres().collectLatest {
                when (it) {
                    is NetworkResponse.Loading -> _genreUIState.postValue(GenresUiState.Loading)

                    is NetworkResponse.Success -> _genreUIState.postValue(it.let { it1 ->
                        GenresUiState.SuccessNowPlaying(it1.data)
                    })

                    is NetworkResponse.Error -> _genreUIState.postValue(it.message.let { it1 ->
                        GenresUiState.Error(
                            it1
                        )
                    })
                }
            }
        }

    }

}