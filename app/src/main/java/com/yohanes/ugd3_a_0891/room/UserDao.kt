package com.yohanes.ugd3_a_0891.room

import androidx.room.*

@Dao
interface UserDao {
    @Insert
    suspend fun addUser(note: User)
    @Update
    suspend fun updateUser(note: User)
    @Delete
    suspend fun deleteUser(note: User)
    @Query("SELECT * FROM user")
    suspend fun getUser() : List<User>
    @Query("SELECT * FROM user WHERE id =:note_id")
    suspend fun getUser(note_id: Int) : List<User>
}