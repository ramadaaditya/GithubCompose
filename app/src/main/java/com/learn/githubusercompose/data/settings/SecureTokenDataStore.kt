package com.learn.githubusercompose.data.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SecureTokenDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val cryptoManager: CryptoManager
) {
    private val GITHUB_TOKEN_KEY = stringPreferencesKey("github_token")

    val githubToken: Flow<String?> =
        dataStore.data.map { prefs ->
            prefs[GITHUB_TOKEN_KEY]?.let { encrypted ->
                cryptoManager.decrypt(encrypted)
            }
        }

    suspend fun saveToken(token: String) {
        dataStore.edit { prefs ->
            prefs[GITHUB_TOKEN_KEY] = cryptoManager.encrypt(token)
        }
    }

    suspend fun clearToken() {
        dataStore.edit { prefs ->
            prefs.remove(GITHUB_TOKEN_KEY)
        }
    }
}