package com.bnawan.gituwu.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.bnawan.gituwu.data.Repository
import com.bnawan.gituwu.data.local.UserDatabase
import com.bnawan.gituwu.data.local.datastore.SettingPreferences
import com.bnawan.gituwu.data.remote.ApiConfig

object Injection {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    fun provideRepository(context: Context): Repository {
        val apiService = ApiConfig.getApiService()
        val database = UserDatabase.getInstance(context)
        val dao = database.userDao()
        val settingPreferences = SettingPreferences.getInstance(context.dataStore)
        return Repository.getInstance(apiService, dao, settingPreferences)
    }
}