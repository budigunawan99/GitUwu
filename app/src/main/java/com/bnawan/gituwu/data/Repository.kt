package com.bnawan.gituwu.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.bnawan.gituwu.data.local.UserDao
import com.bnawan.gituwu.data.remote.ApiService
import com.bnawan.gituwu.model.User

class Repository(private val apiService: ApiService, private val userDao: UserDao) {

    fun searchUser(query: String): LiveData<Result<List<User>>> = liveData {
        emit(Result.Loading)
        try {
            val client = apiService.searchUser(query)
            val items = client.items
            if (items.isEmpty()) {
                emit(Result.Error(MESSAGE_NOT_FOUND))
            } else {
                emit(Result.Success(items))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    suspend fun findUser(username: String): LiveData<Result<User>> = liveData {
        emit(Result.Loading)
        try {
            val savedUser = userDao.getDetailFavoriteUser(username)
            savedUser?.let {
                emit(Result.Success(savedUser))
            } ?: let {
                val user = apiService.getUser(username)
                emit(Result.Success(user))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun findFollower(username: String): LiveData<Result<List<User>>> = liveData {
        emit(Result.Loading)
        try {
            val items = apiService.getFollower(username)
            if (items.isEmpty()) {
                emit(Result.Error(MESSAGE_NOT_FOUND))
            } else {
                emit(Result.Success(items))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun findFollowing(username: String): LiveData<Result<List<User>>> = liveData {
        emit(Result.Loading)
        try {
            val items = apiService.getFollowing(username)
            if (items.isEmpty()) {
                emit(Result.Error(MESSAGE_NOT_FOUND))
            } else {
                emit(Result.Success(items))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    suspend fun getListFavoriteUser(): LiveData<Result<List<User>>> = liveData {
        emit(Result.Loading)
        try {
            val items = userDao.getListFavoriteUser()
            if (items.isEmpty()) {
                emit(Result.Error(MESSAGE_NOT_FOUND))
            } else {
                emit(Result.Success(items))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    suspend fun insertFavoriteUser(user: User) = userDao.insertFavoriteUser(user)

    suspend fun deleteFavoriteUser(user: User) = userDao.deleteFavoriteUser(user)

    companion object {
        private const val MESSAGE_NOT_FOUND = "User Not Found!"

        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            apiService: ApiService,
            userDao: UserDao
        ): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(apiService, userDao)
            }.also { instance = it }
    }
}