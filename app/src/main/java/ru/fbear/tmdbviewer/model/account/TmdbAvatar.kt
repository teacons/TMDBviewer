package ru.fbear.tmdbviewer.model.account


import com.google.gson.annotations.SerializedName

data class TmdbAvatar(
    @SerializedName("avatar_path") val avatarPath: String?
)