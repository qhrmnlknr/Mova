package com.example.movamovieapp.api

import androidx.paging.PagingData
import com.example.movamovieapp.datamodels.local.FavoriteMovies
import com.example.movamovieapp.localstorage.MovieDao
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import retrofit2.Response
import javax.inject.Inject
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.movamovieapp.paging.MoviePagingSource


class Repostory @Inject constructor(private val service: Services, private val firebaseAuth: FirebaseAuth, private val movieDao: MovieDao) {

    suspend fun getGenres() = safeApiRequest {
        service.getGenres()
    }


    suspend fun getNewTvs() = safeApiRequest {
        service.getNewTVs("10749")
    }

    suspend fun getMovies2() = safeApiRequest {
        service.getNewMovies2()
    }

    suspend fun getMovieDetails(id : String) = safeApiRequest {
        service.getMovieDetails(id)
    }

    suspend fun getMovieCast(id : String) = safeApiRequest {
        service.getMovieCast(id)
    }

    suspend fun getMovieTrailers(id : String) = safeApiRequest {
        service.getMovieTrailer(id)
    }

    suspend fun getSimilarMovies(id : String) = safeApiRequest {
        service.getSimilarMovies(id)
    }

    suspend fun searchMovies(keyword : String) = safeApiRequest {
        service.searchMovies(keyword)
    }

    suspend fun addFavorite(favoriteMovies: FavoriteMovies){
        movieDao.addFavorites(favoriteMovies)
    }

    fun getNewMoviesPaging(): Flow<PagingData<com.example.movamovieapp.datamodels.newmovies.Result>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviePagingSource(service) }
        ).flow
    }

    suspend fun addMovieFavoritesChecked(movie : FavoriteMovies){
        val existingMovie = movieDao.getMovieByTitle(movie.title ?: "")
        if (existingMovie == null) {
            movieDao.addFavorites(movie)
        }
    }

    suspend fun getFavorites() : List<FavoriteMovies>{
        return movieDao.getFavoritesLocal()
    }

    suspend fun removeItem(item : FavoriteMovies) {
        movieDao.removeItem(item)
    }



    fun loginUser(
        email: String,
        password: String
    ): Flow<NetworkResponse<AuthResult>> = flow {
        emit(NetworkResponse.Loading())
        val loginUser = firebaseAuth.signInWithEmailAndPassword(email, password).await()
        emit(NetworkResponse.Success(loginUser))
    }.catch {
        emit(NetworkResponse.Error(it.localizedMessage))
    }

    fun registerUser(
        email: String,
        password: String
    ): Flow<NetworkResponse<AuthResult>> = flow {
        emit(NetworkResponse.Loading())
        val registerUser = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
        emit(NetworkResponse.Success(registerUser))
    }.catch {
        emit(NetworkResponse.Error(it.localizedMessage))
    }



    private suspend fun <T : Any> safeApiRequest(apiCall: suspend () -> Response<T>): Flow<NetworkResponse<T>> =
        flow {
            emit(NetworkResponse.Loading())
            try {
                val response = apiCall.invoke()
                if (response.isSuccessful) {
                    response.body()?.let {
                        emit(NetworkResponse.Success(it))
                    } ?: emit(NetworkResponse.Error("Empty Response"))
                } else {
                    emit(NetworkResponse.Error(response.errorBody().toString()))
                }
            } catch (e: Exception) {
                emit(NetworkResponse.Error(e.localizedMessage.toString()))
            }
        }.flowOn(Dispatchers.IO)
}