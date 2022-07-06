package com.bnawan.gituwu.ui.follow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bnawan.gituwu.ui.detail.DetailViewModel

class FollowViewModelFactory(
    private val username: String
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FollowViewModel::class.java)) {
            return FollowViewModel(username) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}