package com.bnawan.gituwu.adapter

import com.bnawan.gituwu.model.User

interface OnUserClickCallback {
    fun onItemClicked(user: User)
}