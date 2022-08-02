package com.bnawan.gituwu.ui.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bnawan.gituwu.data.Repository
import kotlinx.coroutines.launch

class SettingViewModel(
    private val repository: Repository
) : ViewModel() {
    fun saveModeSetting(isDarkModeActive: Boolean) = viewModelScope.launch {
        repository.saveModeSetting(isDarkModeActive)
    }

    fun getModeSettings() = repository.getModeSetting().asLiveData()
}