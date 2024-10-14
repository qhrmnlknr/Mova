package com.example.movamovieapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movamovieapp.api.NetworkResponse
import com.example.movamovieapp.api.Repostory
import com.example.movamovieapp.datamodels.Genre
import com.example.movamovieapp.datamodels.GenreResponse
import com.example.movamovieapp.datamodels.GenreX
import com.example.movamovieapp.datamodels.MovieDetails
import com.example.movamovieapp.datamodels.cast.MovieCast
import com.example.movamovieapp.datamodels.movietrailers.MovieTrailersResponse
import com.example.movamovieapp.datamodels.movietrailers.TrailerResult
import com.example.movamovieapp.datamodels.newmovies.NewMoviesResponse
import com.example.movamovieapp.datamodels.searchmovies.SearchMovieResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.movamovieapp.datamodels.newmovies.Result

@HiltViewModel
class HomeViewModel @Inject constructor (private val repostory: Repostory) : ViewModel() {

    private val _movieUiStateData = MutableLiveData<PagingData<Result>>()
    val movieUiStateData: LiveData<PagingData<Result>> get() = _movieUiStateData

    private val _tvUIState = MutableLiveData<HomeUiState>()
    val tvUIState: LiveData<HomeUiState> get() = _tvUIState

    private val _movieDetailUIState = MutableLiveData<MovieDetailUiState>()
    val movieDetailUIState: LiveData<MovieDetailUiState> get() = _movieDetailUIState


    init {
        getnewmovielist()
        getNewTVs()
        getMovieDetail(id = "453395")
    }


    private fun getnewmovielist(){
        viewModelScope.launch {
            repostory.getNewMoviesPaging().cachedIn(viewModelScope).collectLatest {
                _movieUiStateData.postValue(it)
            }

        }
    }



    private fun getNewTVs(){
        viewModelScope.launch {
            repostory.getNewTvs().collectLatest {
                when (it) {
                    is NetworkResponse.Loading -> _tvUIState.postValue(HomeUiState.Loading)

                    is NetworkResponse.Success -> _tvUIState.postValue(it.let { it1 ->
                        HomeUiState.SuccessNowPlaying(it1.data)
                    })

                    is NetworkResponse.Error -> _tvUIState.postValue(it.message.let { it1 ->
                        HomeUiState.Error(
                            it1
                        )
                    })
                }
            }

        }
    }

    private fun getMovieDetail(id : String){
        viewModelScope.launch {
            repostory.getMovieDetails(id).collectLatest {
                when (it) {
                    is NetworkResponse.Loading -> _movieDetailUIState.postValue(MovieDetailUiState.Loading)

                    is NetworkResponse.Success -> _movieDetailUIState.postValue(it.let { it1 ->
                        MovieDetailUiState.SuccessNowPlaying(it1.data)
                    })

                    is NetworkResponse.Error -> _movieDetailUIState.postValue(it.message.let { it1 ->
                        MovieDetailUiState.Error(
                            it1
                        )
                    })
                }
            }

        }
    }

}

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class SuccessNowPlaying(val data: NewMoviesResponse) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}

sealed class MovieDetailUiState {
    object Loading : MovieDetailUiState()
    data class SuccessNowPlaying(val data: MovieDetails) : MovieDetailUiState()
    data class Error(val message: String) : MovieDetailUiState()
}

sealed class MovieCastUiState {
    object Loading : MovieCastUiState()
    data class SuccessNowPlaying(val data: MovieCast) : MovieCastUiState()
    data class Error(val message: String) : MovieCastUiState()
}

sealed class MovieTrailerUiState {
    object Loading : MovieTrailerUiState()
    data class SuccessNowPlaying(val data: MovieTrailersResponse) : MovieTrailerUiState()
    data class Error(val message: String) : MovieTrailerUiState()
}

sealed class GenresUiState {
    object Loading : GenresUiState()
    data class SuccessNowPlaying(val data: GenreResponse) : GenresUiState()
    data class Error(val message: String) : GenresUiState()
}

sealed class SearchMovieUiState {
    object Loading : SearchMovieUiState()
    data class SuccessNowPlaying(val data: SearchMovieResponse) : SearchMovieUiState()
    data class Error(val message: String) : SearchMovieUiState()
}