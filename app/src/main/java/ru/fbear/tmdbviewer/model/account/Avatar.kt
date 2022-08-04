package ru.fbear.tmdbviewer.model.account


import com.google.gson.annotations.SerializedName

data class Avatar(
    @SerializedName("gravatar") val gravatar: Gravatar?,
    @SerializedName("tmdb") val tmdb: TmdbAvatar?
)