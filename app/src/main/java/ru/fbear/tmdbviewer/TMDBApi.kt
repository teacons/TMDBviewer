package ru.fbear.tmdbviewer

import com.google.gson.annotations.SerializedName
import retrofit2.http.*
import ru.fbear.tmdbviewer.model.account.AccountDetails
import ru.fbear.tmdbviewer.model.auth.AuthToken
import ru.fbear.tmdbviewer.model.auth.NewSession
import ru.fbear.tmdbviewer.model.detail.movie.MovieDetailResponseWithCredits
import ru.fbear.tmdbviewer.model.detail.tv.TVDetailResponseWithCredits
import ru.fbear.tmdbviewer.model.popular.movie.PopularMovies
import ru.fbear.tmdbviewer.model.popular.tv.PopularTVs
import ru.fbear.tmdbviewer.model.search.movie.SearchMovie
import ru.fbear.tmdbviewer.model.search.tv.SearchTV


interface TMDBApi {

    @GET("/3/movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("region") region: String
    ): PopularMovies

    @GET("/3/tv/popular")
    suspend fun getPopularTV(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int,
    ): PopularTVs

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

    @GET("/3/authentication/token/new")
    suspend fun createAuthToken(@Query("api_key") apiKey: String): AuthToken

    @Headers("Content-Type: application/json")
    @POST("/3/authentication/token/validate_with_login")
    suspend fun validateAuthToken(
        @Query("api_key") apiKey: String,
        @Body request: ValidateAuthTokenRequest
    ): AuthToken

    @Headers("Content-Type: application/json")
    @POST("/3/authentication/session/new")
    suspend fun createNewSession(
        @Query("api_key") apiKey: String,
        @Body request: CreateNewTokenRequest
    ): NewSession

    @GET("/3/account")
    suspend fun getAccountDetails(
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String
    ): AccountDetails

}

data class ValidateAuthTokenRequest(
    @SerializedName("password") val password: String,
    @SerializedName("request_token") val requestToken: String,
    @SerializedName("username") val username: String
)

data class CreateNewTokenRequest(
    @SerializedName("request_token") val requestToken: String
)