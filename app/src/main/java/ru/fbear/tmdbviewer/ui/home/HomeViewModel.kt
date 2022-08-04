package ru.fbear.tmdbviewer.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.fbear.tmdbviewer.BuildConfig
import ru.fbear.tmdbviewer.TMDBApi
import ru.fbear.tmdbviewer.model.MovieListResultObject
import ru.fbear.tmdbviewer.model.TVListResultObject
import java.util.*

class HomeViewModel : ViewModel() {

    private val apiKey = BuildConfig.TMDB_API_KEY

    private val api = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(TMDBApi::class.java)

    private val mutableConnectionError = MutableStateFlow(false)
    val connectionError = mutableConnectionError.asStateFlow()

    private val mutablePopularMovies = MutableStateFlow(emptyList<MovieListResultObject>())
    val popularMovies = mutablePopularMovies.asStateFlow()
    private var popularMoviesLastPage = 0

    private val mutablePopularTVs = MutableStateFlow(emptyList<TVListResultObject>())
    val popularTVs = mutablePopularTVs.asStateFlow()
    private var popularTVsLastPage = 0


    fun getPopularMovies() {
        if (popularMoviesLastPage + 1 > 500) return
        val language = Locale.getDefault().toLanguageTag()
        val region = Locale.getDefault().country
        viewModelScope.launch(Dispatchers.IO) {
            try {
                api.getPopularMovies(apiKey, language, popularMoviesLastPage + 1, region)
                    .also {
                        mutablePopularMovies.value = mutablePopularMovies.value + it.results
                        popularMoviesLastPage = it.page
                    }
            } catch (e: Exception) {
                mutableConnectionError.value = true
            }
        }
    }

    fun getPopularTVs() {
        if (popularTVsLastPage + 1 > 500) return
        val language = Locale.getDefault().toLanguageTag()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                api.getPopularTV(apiKey, language, popularTVsLastPage + 1)
                    .also {
                        mutablePopularTVs.value = mutablePopularTVs.value + it.results
                        popularTVsLastPage = it.page
                    }
            } catch (e: Exception) {
                mutableConnectionError.value = true
            }
        }
    }
}