package com.bnawan.gituwu.di

import android.content.Context
import com.bnawan.gituwu.data.Repository
import com.bnawan.gituwu.data.local.UserDatabase
import com.bnawan.gituwu.data.remote.ApiConfig

object Injection {
    fun provideRepository(context: Context): Repository {
        val apiService = ApiConfig.getApiService()
        val database = UserDatabase.getInstance(context)
        val dao = database.userDao()
        return Repository.getInstance(apiService, dao)
    }
}