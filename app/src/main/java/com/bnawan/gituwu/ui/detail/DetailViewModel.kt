package com.bnawan.gituwu.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bnawan.gituwu.data.Repository
import com.bnawan.gituwu.model.User
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: Repository
) : ViewModel() {
    suspend fun findUser(username: String) = repository.findUser(username)

    fun insertFavoriteUser(user: User) = viewModelScope.launch {
        repository.insertFavoriteUser(user)
    }

    fun deleteFavoriteUser(user: User) = viewModelScope.launch {
        repository.deleteFavoriteUser(user)
    }
}