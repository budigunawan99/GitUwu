package com.bnawan.gituwu.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    fun getModeSetting(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[MODE_KEY] ?: false
        }
    }

    suspend fun saveModeSetting(isDarkModeActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[MODE_KEY] = isDarkModeActive
        }
    }

    companion object {
        private val MODE_KEY = booleanPreferencesKey("mode_setting")

        @Volatile
        private var INSTANCE: SettingPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): SettingPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }

}