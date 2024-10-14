package com.example.movamovieapp.localstorage

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movamovieapp.datamodels.local.FavoriteMovies


@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorites(favoritesLocalDTO: FavoriteMovies)

    @Query("SELECT * FROM favorites")
    suspend fun getFavoritesLocal(): List<FavoriteMovies>

    @Query("SELECT * FROM favorites WHERE title = :title LIMIT 1")
    suspend fun getMovieByTitle(title: String): FavoriteMovies

    @Delete
    suspend fun removeItem(favoriteMovie: FavoriteMovies)
}