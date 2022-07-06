package com.bnawan.gituwu.model

import com.google.gson.annotations.SerializedName

data class User(
    @field:SerializedName("login")
    val username: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("html_url")
    val url: String,

    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("company")
    val company: String?,

    @field:SerializedName("location")
    val location: String?,

    @field:SerializedName("public_repos")
    val publicRepos: Int,

    @field:SerializedName("followers")
    val followers: Int,

    @field:SerializedName("following")
    val following: Int,
)