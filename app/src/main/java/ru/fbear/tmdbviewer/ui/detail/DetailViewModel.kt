package ru.fbear.tmdbviewer.ui.detail

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.fbear.tmdbviewer.BuildConfig
import ru.fbear.tmdbviewer.TMDBApi
import ru.fbear.tmdbviewer.model.detail.movie.MovieDetailResponseWithCredits
import ru.fbear.tmdbviewer.model.detail.tv.TVDetailResponseWithCredits
import java.util.*

class DetailViewModel : ViewModel() {

    private val apiKey = BuildConfig.TMDB_API_KEY

    private val api = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(TMDBApi::class.java)

    suspend fun getMovieDetails(id: Int): MovieDetailResponseWithCredits {
        return withContext(Dispatchers.IO) {
            val language = Locale.getDefault().toLanguageTag()
            return@withContext api.getMovieDetailsWithCredits(id, apiKey, language)
        }
    }

    suspend fun getTVDetails(id: Int): TVDetailResponseWithCredits {
        return withContext(Dispatchers.IO) {
            val language = Locale.getDefault().toLanguageTag()
            return@withContext api.getTvDetailsWithCredits(id, apiKey, language)
        }
    }
}