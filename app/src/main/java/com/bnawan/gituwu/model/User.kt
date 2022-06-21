package com.bnawan.gituwu.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val name: String,
    val username: String,
    val avatar: Int,
    val follower: Long,
    val following: Long,
    val location: String,
    val repository: Long,
    val company: String
) : Parcelable