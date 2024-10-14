package com.example.movamovieapp.datamodels.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteMovies(
    @PrimaryKey(autoGenerate = true)
    val id: Int= 0,
    val title: String?,
    val image: String?,
    val vote: Double?
)