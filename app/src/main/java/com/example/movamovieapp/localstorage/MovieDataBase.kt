package com.example.movamovieapp.localstorage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movamovieapp.datamodels.local.FavoriteMovies


@Database(
    entities = [FavoriteMovies::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDataBase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}