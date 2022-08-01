package com.bnawan.gituwu.ui.main

import androidx.lifecycle.ViewModel
import com.bnawan.gituwu.data.Repository

class MainViewModel(private val repository: Repository) : ViewModel() {
    fun searchUser(query: String) = repository.searchUser(query)
}