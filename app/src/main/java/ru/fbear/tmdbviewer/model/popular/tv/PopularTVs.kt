package ru.fbear.tmdbviewer.model.popular.tv


import com.google.gson.annotations.SerializedName
import ru.fbear.tmdbviewer.model.TVListResultObject

data class PopularTVs(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<TVListResultObject>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)