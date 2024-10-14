package com.example.movamovieapp.datamodels


import com.google.gson.annotations.SerializedName

data class Genre(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    val isActive: Boolean = false
)