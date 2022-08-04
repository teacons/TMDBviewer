package ru.fbear.tmdbviewer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.fbear.tmdbviewer.model.MovieListResultObject
import ru.fbear.tmdbviewer.model.TVListResultObject
import ru.fbear.tmdbviewer.model.detail.movie.MovieDetailResponseWithCredits
import ru.fbear.tmdbviewer.model.detail.tv.TVDetailResponseWithCredits
import java.util.*

class TMDBViewModel : ViewModel() {

    private val apiKey = BuildConfig.TMDB_API_KEY

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(TMDBApi::class.java)

    private val mutableConnectionError = MutableStateFlow(false)
    val connectionError = mutableConnectionError.asStateFlow()

    private val mutablePopularMovies = MutableStateFlow(emptyList<MovieListResultObject>())
    val popularMovies = mutablePopularMovies.asStateFlow()
    private var popularMoviesLastPage = 0

    private val mutablePopularTVs = MutableStateFlow(emptyList<TVListResultObject>())
    val popularTVs = mutablePopularTVs.asStateFlow()
    private var popularTVsLastPage = 0

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