package ru.fbear.tmdbviewer.ui.profile

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.fbear.tmdbviewer.*
import ru.fbear.tmdbviewer.model.account.AccountDetails
import java.util.*

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val apiKey = BuildConfig.TMDB_API_KEY

    private val api = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(TMDBApi::class.java)

    private val loginData = LoginData(application.applicationContext)
    private val sessionIdFlow =
        loginData.getSessionId.stateIn(viewModelScope, SharingStarted.Lazily, null)
    private var sessionId: String? = null

    private var mutableIsLogined = MutableStateFlow(false)
    var isLogined = mutableIsLogined.asStateFlow()

    private val mutableAccountDetails = MutableStateFlow<AccountDetails?>(null)
    val accountDetails = mutableAccountDetails.asStateFlow()

    private val favoriteMovieIds = MutableStateFlow(emptyList<Int>())

    private val favoriteTVIds = MutableStateFlow(emptyList<Int>())

    init {
        viewModelScope.launch {
            sessionIdFlow.collect {
                sessionId = it
                mutableIsLogined.value = it != null
                if (it != null) getAccountDetails()
            }
        }
        viewModelScope.launch {
            accountDetails.collect {
                if (it != null) getFavoriteIds(it.id)
            }
        }
    }

    fun isFavorite(id: Int, type: Type): Boolean {
        return when (type) {
            Type.Movie -> id in this.favoriteMovieIds.value
            Type.TV -> id in this.favoriteTVIds.value
        }
    }

    private suspend fun getFavoriteIds(accountId: Int) {
        if (sessionId != null) {
            val language = Locale.getDefault().toLanguageTag()
            withContext(Dispatchers.IO) {
                launch {
                    val favoriteMovieIds = mutableListOf<Int>()
                    for (i in 1..500) {
                        try {
                            favoriteMovieIds.addAll(
                                api.getFavouriteMoviesForAccount(
                                    accountId,
                                    apiKey,
                                    sessionId!!,
                                    language,
                                    i
                                ).results.takeIf { it.isNotEmpty() }?.map { it.id } ?: break)
                        } catch (e: Exception) {
                            break
                        }
                    }
                    this@ProfileViewModel.favoriteMovieIds.value = favoriteMovieIds.toList()
                }
                launch {
                    val favoriteTVIds = mutableListOf<Int>()
                    for (i in 1..500) {
                        try {
                            favoriteTVIds.addAll(
                                api.getFavouriteTVsForAccount(
                                    accountId,
                                    apiKey,
                                    sessionId!!,
                                    language,
                                    i
                                ).results.takeIf { it.isNotEmpty() }?.map { it.id } ?: break)
                        } catch (e: Exception) {
                            break
                        }
                    }
                    this@ProfileViewModel.favoriteTVIds.value = favoriteTVIds.toList()
                }
            }
        }
    }

    suspend fun markAsFavorite(isLiked: Boolean, id: Int, type: Type) {
        return withContext(Dispatchers.IO) {
            if (accountDetails.value != null) {

                val markAnswer = api.markAsFavorite(
                    accountDetails.value!!.id,
                    apiKey,
                    sessionId!!,
                    MarkAsFavoriteRequest(isLiked, id, type.string)
                )
                when (markAnswer.statusCode) {
                    1 -> {
                        when (type) {
                            Type.Movie -> this@ProfileViewModel.favoriteMovieIds.value += id
                            Type.TV -> this@ProfileViewModel.favoriteTVIds.value += id
                        }
                    }
                    13 -> {
                        when (type) {
                            Type.Movie -> this@ProfileViewModel.favoriteMovieIds.value -= id
                            Type.TV -> this@ProfileViewModel.favoriteTVIds.value -= id
                        }
                    }
                    12 -> Unit
                    else -> throw IllegalStateException("statusCode = ${markAnswer.statusCode} message = ${markAnswer.statusMessage}")
                }
            }
        }
    }

    suspend fun login(login: String, password: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val authToken = api.createAuthToken(apiKey)

                if (!authToken.success) return@withContext authToken.success

                val validatedAuthToken = api.validateAuthToken(
                    apiKey,
                    ValidateAuthTokenRequest(
                        username = login,
                        password = password,
                        requestToken = authToken.requestToken
                    )
                )

                if (!validatedAuthToken.success) return@withContext validatedAuthToken.success

                val newSession =
                    api.createNewSession(
                        apiKey,
                        CreateNewTokenRequest(requestToken = validatedAuthToken.requestToken)
                    )

                if (!newSession.success) return@withContext newSession.success

                loginData.setSessionId(newSession.sessionId)

                return@withContext newSession.success
            } catch (e: Exception) {
                return@withContext false
            }
        }
    }

    private suspend fun getAccountDetails() {
        try {
            mutableAccountDetails.value = api.getAccountDetails(apiKey, sessionId!!)
        } catch (e: Exception) {
//            TODO()
        }
    }

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            mutableAccountDetails.value = null
            loginData.removeSessionId()
        }
    }

}

class LoginData(private val context: Context) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("login_data")
        val SESSION_ID = stringPreferencesKey("session_id")
    }

    val getSessionId: Flow<String?> = context.dataStore.data
        .map { loginData ->
            loginData[SESSION_ID]
        }

    suspend fun setSessionId(sessionId: String) {
        context.dataStore.edit { loginData ->
            loginData[SESSION_ID] = sessionId
        }
    }

    suspend fun removeSessionId() {
        context.dataStore.edit { loginData ->
            loginData.remove(SESSION_ID)
        }
    }
}