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
import ru.fbear.tmdbviewer.BuildConfig
import ru.fbear.tmdbviewer.CreateNewTokenRequest
import ru.fbear.tmdbviewer.TMDBApi
import ru.fbear.tmdbviewer.ValidateAuthTokenRequest
import ru.fbear.tmdbviewer.model.account.AccountDetails

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

    init {
        viewModelScope.launch {
            sessionIdFlow.collect {
                sessionId = it
                mutableIsLogined.value = it != null
                if (it != null) getAccountDetails()
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