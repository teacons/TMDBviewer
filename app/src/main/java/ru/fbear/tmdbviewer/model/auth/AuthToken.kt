package ru.fbear.tmdbviewer.model.auth


import com.google.gson.annotations.SerializedName

data class AuthToken(
    @SerializedName("expires_at") val expiresAt: String,
    @SerializedName("request_token") val requestToken: String,
    @SerializedName("success") val success: Boolean
)