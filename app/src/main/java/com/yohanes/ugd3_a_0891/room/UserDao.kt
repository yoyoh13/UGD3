package com.yohanes.ugd3_a_0891.room

import androidx.room.*

@Dao
interface UserDao {
    @Insert
    suspend fun addUser(user: User)
    @Update
    suspend fun updateUser(user: User)
    @Delete
    suspend fun deleteUser(user: User)
    @Query("SELECT * FROM user")
    suspend fun getUser() : List<User>
    @Query("SELECT * FROM user WHERE username LIKE :username")
    fun getUser(username: String) : List<User>
}