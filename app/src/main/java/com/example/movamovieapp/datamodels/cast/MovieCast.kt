package com.example.movamovieapp.datamodels.cast


import com.google.gson.annotations.SerializedName

data class MovieCast(
    @SerializedName("cast")
    val cast: List<Cast>?,
    @SerializedName("crew")
    val crew: List<Crew>?,
    @SerializedName("id")
    val id: Int?
)