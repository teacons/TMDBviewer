package ru.fbear.tmdbviewer.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.fbear.tmdbviewer.BuildConfig
import ru.fbear.tmdbviewer.TMDBApi
import ru.fbear.tmdbviewer.model.MovieListResultObject
import ru.fbear.tmdbviewer.model.TVListResultObject
import java.util.*

class SearchViewModel : ViewModel() {
    private val apiKey = BuildConfig.TMDB_API_KEY

    private val api = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(TMDBApi::class.java)

    private val mutableConnectionError = MutableStateFlow(false)
    val connectionError = mutableConnectionError.asStateFlow()

    private val mutableSearchTag = MutableStateFlow("")
    val searchTag = mutableSearchTag.asStateFlow()

    private val mutableSearchedMovies = MutableStateFlow(emptyList<MovieListResultObject>())
    val searchedMovies = mutableSearchedMovies.asStateFlow()
    private val mutableSearchedMoviesTotalResults = MutableStateFlow(0)
    val searchedMoviesTotalResults = mutableSearchedMoviesTotalResults.asStateFlow()
    private var searchedMoviesLastPage = 0

    private val mutableSearchedTVs = MutableStateFlow(emptyList<TVListResultObject>())
    val searchedTVs = mutableSearchedTVs.asStateFlow()
    private val mutableSearchedTVsTotalResults = MutableStateFlow(0)
    val searchedTVsTotalResults = mutableSearchedTVsTotalResults.asStateFlow()
    private var searchedTVsLastPage = 0

    init {
        viewModelScope.launch {
            searchTag.collect {
                searchPreview(it)
            }
        }
    }

    fun updateSearchTag(searchTag: String) {
        mutableSearchTag.value = searchTag
    }

    private suspend fun searchPreview(searchTag: String) {
        if (searchTag.isEmpty()) {
            mutableSearchedMovies.value = emptyList()
            mutableSearchedTVs.value = emptyList()
            return
        }
        val language = Locale.getDefault().toLanguageTag()
        val region = Locale.getDefault().country
        withContext(Dispatchers.IO) {
            try {
                api.searchMovie(apiKey, language, searchTag, region, 1)
                    .also {
                        mutableSearchedMovies.value = it.results
                        mutableSearchedMoviesTotalResults.value = it.totalResults
                        searchedMoviesLastPage = it.page
                    }
                api.searchTV(apiKey, language, searchTag, 1)
                    .also {
                        mutableSearchedTVs.value = it.results
                        mutableSearchedTVsTotalResults.value = it.totalResults
                        searchedTVsLastPage = it.page
                    }
            } catch (e: Exception) {
                mutableConnectionError.value = true
            }
        }
    }

    fun searchMovies() {
        if (searchedMoviesLastPage + 1 > 500 || searchedTVsLastPage + 1 > 500) return
        val language = Locale.getDefault().toLanguageTag()
        val region = Locale.getDefault().country
        viewModelScope.launch(Dispatchers.IO) {
            try {
                api.searchMovie(
                    apiKey,
                    language,
                    searchTag.value,
                    region,
                    searchedMoviesLastPage + 1
                ).also {
                    mutableSearchedMovies.value = mutableSearchedMovies.value + it.results
                    searchedMoviesLastPage = it.page
                }
            } catch (e: Exception) {
                mutableConnectionError.value = true
            }
        }
    }

    fun searchTV() {
        if (searchedMoviesLastPage + 1 > 500 || searchedTVsLastPage + 1 > 500) return
        val language = Locale.getDefault().toLanguageTag()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                api.searchTV(apiKey, language, searchTag.value, searchedTVsLastPage + 1)
                    .also {
                        mutableSearchedTVs.value = mutableSearchedTVs.value + it.results
                        searchedTVsLastPage = it.page
                    }
            } catch (e: Exception) {
                mutableConnectionError.value = true
            }
        }
    }
}