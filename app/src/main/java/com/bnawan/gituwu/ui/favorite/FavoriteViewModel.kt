package com.bnawan.gituwu.ui.favorite

import androidx.lifecycle.ViewModel
import com.bnawan.gituwu.data.Repository

class FavoriteViewModel(private val repository: Repository) : ViewModel() {
    suspend fun getListFavoriteUser() = repository.getListFavoriteUser()
}