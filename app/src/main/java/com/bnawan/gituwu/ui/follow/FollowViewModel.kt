package com.bnawan.gituwu.ui.follow

import androidx.lifecycle.ViewModel
import com.bnawan.gituwu.data.Repository

class FollowViewModel(
    private val repository: Repository
) : ViewModel() {

    fun findFollower(username: String) = repository.findFollower(username)
    fun findFollowing(username: String) = repository.findFollowing(username)
}