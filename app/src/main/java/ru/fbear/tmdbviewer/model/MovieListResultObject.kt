package ru.fbear.tmdbviewer.model


import com.google.gson.annotations.SerializedName

data class MovieListResultObject(
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("genre_ids") val genreIds: List<Int>,
    @SerializedName("id") override val id: Int,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("popularity") val popularity: Float,
    @SerializedName("poster_path") override val posterPath: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("title") override val name: String,
    @SerializedName("video") val video: Boolean,
    @SerializedName("vote_average") override val voteAverage: Float,
    @SerializedName("vote_count") val voteCount: Int
) : HomeGridEntry, SearchListEntry