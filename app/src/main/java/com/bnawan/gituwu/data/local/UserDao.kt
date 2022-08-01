package com.bnawan.gituwu.data.local

import androidx.room.*
import com.bnawan.gituwu.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user ORDER BY username")
    suspend fun getListFavoriteUser(): List<User>

    @Query("SELECT * FROM user WHERE username = :username")
    suspend fun getDetailFavoriteUser(username: String): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteUser(user: User)

    @Delete
    suspend fun deleteFavoriteUser(user: User)
}