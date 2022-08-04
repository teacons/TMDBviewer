package ru.fbear.tmdbviewer

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.fbear.tmdbviewer.model.MovieListResultObject
import ru.fbear.tmdbviewer.model.TVListResultObject
import ru.fbear.tmdbviewer.model.detail.movie.MovieDetailResponseWithCredits
import ru.fbear.tmdbviewer.model.detail.tv.TVDetailResponseWithCredits
import ru.fbear.tmdbviewer.model.search.movie.SearchMovie
import ru.fbear.tmdbviewer.model.search.tv.SearchTV

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

    @GET("/3/search/movie")
    suspend fun searchMovie(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("query") query: String,
        @Query("region") region: String,
        @Query("page") page: Int,
    ): SearchMovie

    @GET("/3/search/tv")
    suspend fun searchTV(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("query") query: String,
        @Query("page") page: Int,
    ): SearchTV

    @GET("/3/configuration")
    fun getConfiguration(@Query("api_key") apiKey: String)

}


data class PopularMovieResponse(
    val page: Int,
    val results: List<MovieListResultObject>,
    val totalResults: Int,
    val totalPages: Int
)

data class PopularTVResponse(
    val page: Int,
    val results: List<TVListResultObject>,
    val totalResults: Int,
    val totalPages: Int
)

interface HomeGridEntry {
    val id: Int
    val posterPath: String?
    val name: String
}

interface SearchListEntry {
    val id: Int
    val posterPath: String?
    val name: String
    val voteAverage: Float
}