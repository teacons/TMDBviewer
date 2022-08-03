package ru.fbear.tmdbviewer

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.fbear.tmdbviewer.model.moviedetail.MovieDetailResponseWithCredits
import ru.fbear.tmdbviewer.model.tvdetail.TVDetailResponseWithCredits

interface TMDBApi {

    @GET("/3/movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("region") region: String
    ): PopularMovieResponse

    @GET("/3/tv/popular")
    suspend fun getPopularTV(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int,
    ): PopularTVResponse

    @GET("/3/movie/{id}?append_to_response=credits")
    suspend fun getMovieDetailsWithCredits(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
    ): MovieDetailResponseWithCredits

    @GET("/3/tv/{id}?append_to_response=credits")
    suspend fun getTvDetailsWithCredits(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
    ): TVDetailResponseWithCredits

    @GET("/3/configuration")
    fun getConfiguration(@Query("api_key") apiKey: String)

}


data class PopularMovieResponse(
    val page: Int,
    val results: List<MoviesListEntry>,
    val totalResults: Int,
    val totalPages: Int
)

data class PopularTVResponse(
    val page: Int,
    val results: List<TVListEntry>,
    val totalResults: Int,
    val totalPages: Int
)

interface HomeGridEntry {
    val id: Int
    val posterPath: String?
    val name: String
}

interface SearchListEntry {
    val posterPath: String?
    val name: String
    val voteAverage: Float
}

interface DetailsEntry {
    val name: String
    val releaseDate: String

    val posterPath: String?
}

data class MoviesListEntry(
    @SerializedName("poster_path")
    override val posterPath: String?,
    val adult: Boolean,
    val overview: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("genre_ids")
    val genreIds: List<Int>,
    override val id: Int,
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("title")
    override val name: String,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    val popularity: Float,
    @SerializedName("vote_count")
    val voteCount: Int,
    val video: Boolean,
    @SerializedName("vote_average")
    override val voteAverage: Float
) : HomeGridEntry, SearchListEntry

data class TVListEntry(
    @SerializedName("poster_path")
    override val posterPath: String?,
    val popularity: Float,
    override val id: Int,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("vote_average")
    override val voteAverage: Float,
    val overview: String,
    @SerializedName("first_air_date")
    val firstAirDate: String,
    @SerializedName("origin_country")
    val originCountry: List<String>,
    @SerializedName("genre_ids")
    val genreIds: List<Int>,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("vote_count")
    val voteCount: Int,
    override val name: String,
    @SerializedName("original_name")
    val originalName: String
) : HomeGridEntry, SearchListEntry