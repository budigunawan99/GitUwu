package com.bnawan.gituwu.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bnawan.gituwu.data.Repository
import kotlinx.coroutines.Dispatchers

class SplashViewModel(
    private val repository: Repository
) : ViewModel() {
    fun getModeSettings() = repository.getModeSetting().asLiveData(Dispatchers.IO)
}