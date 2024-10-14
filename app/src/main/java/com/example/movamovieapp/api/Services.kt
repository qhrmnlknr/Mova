package com.example.movamovieapp.api

import com.example.movamovieapp.datamodels.GenreResponse
import com.example.movamovieapp.datamodels.MovieDetails
import com.example.movamovieapp.datamodels.cast.MovieCast
import com.example.movamovieapp.datamodels.movietrailers.MovieTrailersResponse
import com.example.movamovieapp.datamodels.newmovies.NewMoviesResponse
import com.example.movamovieapp.datamodels.searchmovies.SearchMovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Services {

    @GET("genre/movie/list")
    suspend fun getGenres(): Response<GenreResponse>

    @GET("discover/movie")
    suspend fun getNewMovies(
        @Query("page") keyword: Int
    ): Response<NewMoviesResponse>

    @GET("discover/movie")
    suspend fun getNewMovies2(): Response<NewMoviesResponse>

    @GET("discover/movie")
    suspend fun getNewTVs(
        @Query("with_genres") id: String
    ): Response<NewMoviesResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movieId: String): Response<MovieDetails>

    @GET("movie/{movie_id}/casts")
    suspend fun getMovieCast(@Path("movie_id") movieId: String): Response<MovieCast>

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieTrailer(@Path("movie_id") id: String): Response<MovieTrailersResponse>

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(@Path("movie_id") id: String): Response<NewMoviesResponse>

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") keyword: String,
    ): Response<SearchMovieResponse>




}