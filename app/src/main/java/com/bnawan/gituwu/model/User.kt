package com.bnawan.gituwu.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user")
data class User(
    @field:SerializedName("login")
    @ColumnInfo(name = "username")
    val username: String,

    @field:SerializedName("id")
    @ColumnInfo(name = "id")
    @PrimaryKey
    val id: Int,

    @field:SerializedName("html_url")
    @ColumnInfo(name = "url")
    val url: String,

    @field:SerializedName("avatar_url")
    @ColumnInfo(name = "avatarUrl")
    val avatarUrl: String,

    @field:SerializedName("name")
    @ColumnInfo(name = "name")
    val name: String,

    @field:SerializedName("company")
    @ColumnInfo(name = "company")
    val company: String?,

    @field:SerializedName("location")
    @ColumnInfo(name = "location")
    val location: String?,

    @field:SerializedName("public_repos")
    @ColumnInfo(name = "publicRepos")
    val publicRepos: Int,

    @field:SerializedName("followers")
    @ColumnInfo(name = "followers")
    val followers: Int,

    @field:SerializedName("following")
    @ColumnInfo(name = "following")
    val following: Int,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean? = false
)