package com.bnawan.gituwu.data

import com.bnawan.gituwu.model.SearchResponse
import com.bnawan.gituwu.model.User
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun searchUser(
        @Query("q")
        query: String
    ): Call<SearchResponse>

    @GET("users/{username}")
    fun getUser(
        @Path("username")
        username: String
    ): Call<User>

    @GET("users/{username}/followers")
    fun getFollower(
        @Path("username")
        username: String
    ): Call<List<User>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username")
        username: String
    ): Call<List<User>>
}