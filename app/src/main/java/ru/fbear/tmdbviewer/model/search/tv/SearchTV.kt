package ru.fbear.tmdbviewer.model.search.tv


import com.google.gson.annotations.SerializedName
import ru.fbear.tmdbviewer.model.TVListResultObject

data class SearchTV(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<TVListResultObject>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)