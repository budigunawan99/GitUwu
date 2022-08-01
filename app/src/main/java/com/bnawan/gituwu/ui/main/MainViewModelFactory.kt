package com.bnawan.gituwu.ui.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bnawan.gituwu.data.Repository
import com.bnawan.gituwu.di.Injection

class MainViewModelFactory(private val repository: Repository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: MainViewModelFactory? = null
        fun getInstance(context: Context): MainViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: MainViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}