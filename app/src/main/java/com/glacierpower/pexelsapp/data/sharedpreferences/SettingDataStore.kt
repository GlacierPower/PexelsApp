package com.glacierpower.pexelsapp.data.sharedpreferences

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.emptyPreferences
import androidx.datastore.preferences.preferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class SettingDataStore @Inject constructor(
    context: Context
) {
    companion object {
        private const val DATA_STORE_NAME = "setting_dark_mode.pref"
        private val IS_DARK_MODE = preferencesKey<Boolean>("is_dark_mode")

    }

    private val appContext = context.applicationContext
    private val dataStore: DataStore<Preferences> = appContext.createDataStore(
        name = DATA_STORE_NAME
    )



    suspend fun setDarkMode(uiMode: UiMode) {
        dataStore.edit { preferences ->
            preferences[IS_DARK_MODE] = when (uiMode) {
                UiMode.LIGHT -> false
                UiMode.DARK -> true
            }
        }
    }

    val uiModeFlow: Flow<UiMode> = dataStore.data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            when (preferences[IS_DARK_MODE] ?: false) {
                true -> UiMode.DARK
                false -> UiMode.LIGHT
            }
        }
}