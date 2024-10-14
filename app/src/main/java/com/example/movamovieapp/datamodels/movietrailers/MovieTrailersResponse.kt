package com.example.movamovieapp.datamodels.movietrailers


import com.google.gson.annotations.SerializedName

data class MovieTrailersResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("results")
    val results: List<TrailerResult>?
)