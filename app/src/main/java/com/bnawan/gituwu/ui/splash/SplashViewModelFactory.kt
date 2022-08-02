package com.bnawan.gituwu.ui.splash

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bnawan.gituwu.data.Repository
import com.bnawan.gituwu.di.Injection

class SplashViewModelFactory(
    private val repository: Repository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            return SplashViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: SplashViewModelFactory? = null
        fun getInstance(context: Context): SplashViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: SplashViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }

}