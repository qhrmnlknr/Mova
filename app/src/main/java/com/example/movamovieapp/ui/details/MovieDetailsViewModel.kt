package com.example.movamovieapp.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movamovieapp.api.NetworkResponse
import com.example.movamovieapp.api.Repostory
import com.example.movamovieapp.datamodels.GenreX
import com.example.movamovieapp.datamodels.cast.Cast
import com.example.movamovieapp.datamodels.cast.MovieCast
import com.example.movamovieapp.datamodels.local.FavoriteMovies
import com.example.movamovieapp.datamodels.movietrailers.TrailerResult
import com.example.movamovieapp.datamodels.newmovies.NewMoviesResponse
import com.example.movamovieapp.ui.home.HomeUiState
import com.example.movamovieapp.ui.home.MovieCastUiState
import com.example.movamovieapp.ui.home.MovieDetailUiState
import com.example.movamovieapp.ui.home.MovieTrailerUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(val repostory: Repostory) :ViewModel() {

    private val _movieDetailUIState = MutableLiveData<MovieDetailUiState>()
    val movieDetailUIState: LiveData<MovieDetailUiState> get() = _movieDetailUIState

    private val _movieCastUIState = MutableLiveData<MovieCastUiState>()
    val movieCastUIState: LiveData<MovieCastUiState> get() = _movieCastUIState

    private val _movieTrailerUIState = MutableLiveData<MovieTrailerUiState>()
    val movieTrailerUIState: LiveData<MovieTrailerUiState> get() = _movieTrailerUIState

    private val _movieSimilarUIState = MutableLiveData<HomeUiState>()
    val movieSimilarUIState: LiveData<HomeUiState> get() = _movieSimilarUIState

    fun getMovieDetail(id : String){
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

    fun getMovieCast(id : String){
        viewModelScope.launch {
            repostory.getMovieCast(id).collectLatest {
                when (it) {
                    is NetworkResponse.Loading -> _movieCastUIState.postValue(MovieCastUiState.Loading)

                    is NetworkResponse.Success -> _movieCastUIState.postValue(it.let { it1 ->
                        MovieCastUiState.SuccessNowPlaying(it1.data)
                    })

                    is NetworkResponse.Error -> _movieCastUIState.postValue(it.message.let { it1 ->
                        MovieCastUiState.Error(
                            it1
                        )
                    })
                }
            }
        }
    }


    fun getMovieTrailers(id : String){
        viewModelScope.launch {
            repostory.getMovieTrailers(id).collectLatest {
                when (it) {
                    is NetworkResponse.Loading -> _movieTrailerUIState.postValue(MovieTrailerUiState.Loading)

                    is NetworkResponse.Success -> _movieTrailerUIState.postValue(it.data?.let { it1 ->
                        MovieTrailerUiState.SuccessNowPlaying(it1)
                    })

                    is NetworkResponse.Error -> _movieTrailerUIState.postValue(it.message.let { it1 ->
                        MovieTrailerUiState.Error(
                            it1
                        )
                    })
                }
            }
        }
    }

    fun getSimilarMovies(id : String){
        viewModelScope.launch {
            repostory.getSimilarMovies(id).collectLatest {
                when (it) {
                    is NetworkResponse.Loading -> _movieSimilarUIState.postValue(HomeUiState.Loading)

                    is NetworkResponse.Success -> _movieSimilarUIState.postValue(it.data?.let { it1 ->
                        HomeUiState.SuccessNowPlaying(it1)
                    })

                    is NetworkResponse.Error -> _movieSimilarUIState.postValue(it.message.let { it1 ->
                        HomeUiState.Error(
                            it1
                        )
                    })
                }
            }
        }
    }

    fun addToFavorites(favoriteMovies: FavoriteMovies){
        viewModelScope.launch {
            repostory.addMovieFavoritesChecked(favoriteMovies)
        }
    }




    fun getActors(movieCast: MovieCast): List<Cast>? {
        val actors = movieCast.cast?.filter {  it.knownForDepartment == "Acting" }
        return actors
    }

    fun getGenres(genres: List<GenreX>) : String{
        val genreNames = genres.map { genre -> genre.name }
        val text = genreNames.joinToString(", ")
        return text
    }




}