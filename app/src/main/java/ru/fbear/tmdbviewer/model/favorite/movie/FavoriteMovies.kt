package ru.fbear.tmdbviewer.model.favorite.movie


import com.google.gson.annotations.SerializedName
import ru.fbear.tmdbviewer.model.MovieListResultObject

data class FavoriteMovies(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<MovieListResultObject>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)