package com.bnawan.gituwu.data.remote

import com.bnawan.gituwu.model.SearchResponse
import com.bnawan.gituwu.model.User
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    suspend fun searchUser(
        @Query("q")
        query: String
    ): SearchResponse

    @GET("users/{username}")
    suspend fun getUser(
        @Path("username")
        username: String
    ): User

    @GET("users/{username}/followers")
    suspend fun getFollower(
        @Path("username")
        username: String
    ): List<User>

    @GET("users/{username}/following")
    suspend fun getFollowing(
        @Path("username")
        username: String
    ): List<User>
}