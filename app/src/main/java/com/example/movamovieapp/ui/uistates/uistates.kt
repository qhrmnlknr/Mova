package com.example.movamovieapp.ui.uistates

import com.example.movamovieapp.datamodels.GenreResponse
import com.example.movamovieapp.datamodels.MovieDetails
import com.example.movamovieapp.datamodels.cast.MovieCast
import com.example.movamovieapp.datamodels.movietrailers.MovieTrailersResponse
import com.example.movamovieapp.datamodels.newmovies.NewMoviesResponse
import com.example.movamovieapp.datamodels.searchmovies.SearchMovieResponse

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