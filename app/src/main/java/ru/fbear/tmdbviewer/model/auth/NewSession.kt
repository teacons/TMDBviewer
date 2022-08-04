package ru.fbear.tmdbviewer.model.auth


import com.google.gson.annotations.SerializedName

data class NewSession(
    @SerializedName("session_id") val sessionId: String,
    @SerializedName("success") val success: Boolean
)