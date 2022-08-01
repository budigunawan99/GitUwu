package com.bnawan.gituwu.ui.follow

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bnawan.gituwu.data.Repository
import com.bnawan.gituwu.di.Injection

class FollowViewModelFactory(
    private val repository: Repository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FollowViewModel::class.java)) {
            return FollowViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: FollowViewModelFactory? = null
        fun getInstance(context: Context): FollowViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: FollowViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}